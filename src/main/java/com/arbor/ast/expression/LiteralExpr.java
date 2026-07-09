package com.arbor.ast.expression;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class LiteralExpr extends Expr {
    private final Object value;

    public LiteralExpr(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteralExpr(this);
    }
}
