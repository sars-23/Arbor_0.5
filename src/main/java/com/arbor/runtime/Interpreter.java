package com.arbor.runtime;

import com.arbor.ast.Expr;
import com.arbor.ast.Stmt;
import com.arbor.ast.expression.*;
import com.arbor.ast.statement.*;
import com.arbor.error.RuntimeError;
import com.arbor.lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {
    public final Environment globals = new Environment();
    private Environment environment = globals;

    public void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError error) {
            System.err.println(error.getMessage());
        }
    }

    private void execute(Stmt stmt) {
        if (stmt != null) {
            stmt.accept(this);
        }
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Void visitBlockStmt(BlockStmt stmt) {
        executeBlock(stmt.getStatements(), new Environment(environment));
        return null;
    }

    public void executeBlock(List<Stmt> statements, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;
            for (Stmt statement : statements) {
                execute(statement);
            }
        } finally {
            this.environment = previous;
        }
    }

    @Override
    public Void visitExpressionStmt(ExpressionStmt stmt) {
        evaluate(stmt.getExpression());
        return null;
    }

    @Override
    public Void visitFunctionStmt(FunctionStmt stmt) {
        ArborFunction function = new ArborFunction(stmt, environment);
        environment.define(stmt.getName().getLexeme(), function);
        return null;
    }

    @Override
    public Void visitIfStmt(IfStmt stmt) {
        if (isTruthy(evaluate(stmt.getCondition()))) {
            execute(stmt.getThenBranch());
        } else if (stmt.getElseBranch() != null) {
            execute(stmt.getElseBranch());
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(PrintStmt stmt) {
        Object value = evaluate(stmt.getExpression());
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitReturnStmt(ReturnStmt stmt) {
        Object value = null;
        if (stmt.getValue() != null) {
            value = evaluate(stmt.getValue());
        }
        throw new Return(value);
    }

    @Override
    public Void visitVarStmt(VarStmt stmt) {
        Object value = null;
        if (stmt.getInitializer() != null) {
            value = evaluate(stmt.getInitializer());
        }
        environment.define(stmt.getName().getLexeme(), value);
        return null;
    }

    @Override
    public Void visitWhileStmt(WhileStmt stmt) {
        while (isTruthy(evaluate(stmt.getCondition()))) {
            execute(stmt.getBody());
        }
        return null;
    }

    @Override
    public Object visitAssignExpr(AssignExpr expr) {
        Object value = evaluate(expr.getValue());
        environment.assign(expr.getName(), value);
        return value;
    }

    @Override
    public Object visitBinaryExpr(BinaryExpr expr) {
        Object left = evaluate(expr.getLeft());
        Object right = evaluate(expr.getRight());

        switch (expr.getOperator().getType()) {
            case MINUS:
                checkNumberOperands(expr.getOperator(), left, right);
                return (double) left - (double) right;
            case SLASH:
                checkNumberOperands(expr.getOperator(), left, right);
                if ((double) right == 0) {
                    throw new RuntimeError(expr.getOperator(), "Division by zero.");
                }
                return (double) left / (double) right;
            case STAR:
                checkNumberOperands(expr.getOperator(), left, right);
                return (double) left * (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if (left instanceof String || right instanceof String) {
                    return stringify(left) + stringify(right);
                }
                throw new RuntimeError(expr.getOperator(), "Operands must be two numbers or two strings.");
            case GREATER:
                checkNumberOperands(expr.getOperator(), left, right);
                return (double) left > (double) right;
            case GREATER_EQUAL:
                checkNumberOperands(expr.getOperator(), left, right);
                return (double) left >= (double) right;
            case LESS:
                checkNumberOperands(expr.getOperator(), left, right);
                return (double) left < (double) right;
            case LESS_EQUAL:
                checkNumberOperands(expr.getOperator(), left, right);
                return (double) left <= (double) right;
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
        }

        return null;
    }

    @Override
    public Object visitCallExpr(CallExpr expr) {
        Object callee = evaluate(expr.getCallee());
        List<Object> arguments = new ArrayList<>();
        for (Expr argument : expr.getArguments()) {
            arguments.add(evaluate(argument));
        }

        if (!(callee instanceof ArborCallable)) {
            throw new RuntimeError(expr.getParen(), "Can only call functions and classes.");
        }

        ArborCallable function = (ArborCallable) callee;
        if (arguments.size() != function.arity()) {
            throw new RuntimeError(expr.getParen(), "Expected " +
                function.arity() + " arguments but got " +
                arguments.size() + ".");
        }

        return function.call(this, arguments);
    }

    @Override
    public Object visitGroupingExpr(GroupingExpr expr) {
        return evaluate(expr.getExpression());
    }

    @Override
    public Object visitLiteralExpr(LiteralExpr expr) {
        return expr.getValue();
    }

    @Override
    public Object visitUnaryExpr(UnaryExpr expr) {
        Object right = evaluate(expr.getRight());

        switch (expr.getOperator().getType()) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                checkNumberOperand(expr.getOperator(), right);
                return -(double) right;
        }

        return null;
    }

    @Override
    public Object visitVariableExpr(VariableExpr expr) {
        return environment.get(expr.getName());
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean) object;
        return true;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
        return a.equals(b);
    }

    private String stringify(Object object) {
        if (object == null) return "null";
        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }
        return object.toString();
    }
}
