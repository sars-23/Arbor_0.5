package com.arbor.ast.expression;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class UnaryExpr extends Expr {
    private final Token operator;
    private final Expr right;

    public UnaryExpr(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getRight() {
        return right;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitUnaryExpr(this);
    }
}
