package com.arbor.ast.expression;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class GroupingExpr extends Expr {
    private final Expr expression;

    public GroupingExpr(Expr expression) {
        this.expression = expression;
    }

    public Expr getExpression() {
        return expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitGroupingExpr(this);
    }
}
