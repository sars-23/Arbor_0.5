package com.arbor.error;

import com.arbor.lexer.Token;

public class RuntimeError extends ArborError {
    private final Token token;

    public RuntimeError(Token token, String message) {
        super(message + " at line " + token.getLine() + ".");
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
