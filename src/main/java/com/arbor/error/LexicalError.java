package com.arbor.error;

public class LexicalError extends ArborError {
    private final int line;

    public LexicalError(String message, int line) {
        super(message + " at line " + line + ".");
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
