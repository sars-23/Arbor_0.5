package com.arbor.ast.expression;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class BinaryExpr extends Expr {
    private final Expr left;
    private final Token operator;
    private final Expr right;

    public BinaryExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expr getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getRight() {
        return right;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitBinaryExpr(this);
    }
}
