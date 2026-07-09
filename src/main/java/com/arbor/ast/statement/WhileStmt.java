package com.arbor.ast.statement;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class WhileStmt extends Stmt {
    private final Expr condition;
    private final Stmt body;

    public WhileStmt(Expr condition, Stmt body) {
        this.condition = condition;
        this.body = body;
    }

    public Expr getCondition() {
        return condition;
    }

    public Stmt getBody() {
        return body;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitWhileStmt(this);
    }
}
