package com.arbor.ast.expression;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class AssignExpr extends Expr {
    private final Token name;
    private final Expr value;

    public AssignExpr(Token name, Expr value) {
        this.name = name;
        this.value = value;
    }

    public Token getName() {
        return name;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAssignExpr(this);
    }
}
