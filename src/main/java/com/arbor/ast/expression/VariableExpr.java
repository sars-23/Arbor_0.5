package com.arbor.ast.expression;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class VariableExpr extends Expr {
    private final Token name;

    public VariableExpr(Token name) {
        this.name = name;
    }

    public Token getName() {
        return name;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitVariableExpr(this);
    }
}
