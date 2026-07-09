package com.arbor.ast.statement;

import com.arbor.ast.*;
import com.arbor.lexer.Token;
import java.util.List;

public class ReturnStmt extends Stmt {
    private final Token keyword;
    private final Expr value;

    public ReturnStmt(Token keyword, Expr value) {
        this.keyword = keyword;
        this.value = value;
    }

    public Token getKeyword() {
        return keyword;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitReturnStmt(this);
    }
}
