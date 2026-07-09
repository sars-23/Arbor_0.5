package com.arbor.ast.statement;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class PrintStmt extends Stmt {
    private final Expr expression;

    public PrintStmt(Expr expression) {
        this.expression = expression;
    }

    public Expr getExpression() {
        return expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitPrintStmt(this);
    }
}
