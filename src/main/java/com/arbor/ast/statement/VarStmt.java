package com.arbor.ast.statement;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class VarStmt extends Stmt {
    private final Token name;
    private final Expr initializer;

    public VarStmt(Token name, Expr initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public Token getName() {
        return name;
    }

    public Expr getInitializer() {
        return initializer;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitVarStmt(this);
    }
}
