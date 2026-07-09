package com.arbor.lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {

    @Test
    public void testBasicTokens() {
        Lexer lexer = new Lexer("let x = 10 + 20;");
        List<Token> tokens = lexer.scanTokens();

        assertEquals(8, tokens.size());
        assertEquals(TokenType.LET, tokens.get(0).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("x", tokens.get(1).getLexeme());
        assertEquals(TokenType.EQUAL, tokens.get(2).getType());
        assertEquals(TokenType.NUMBER, tokens.get(3).getType());
        assertEquals(10.0, tokens.get(3).getLiteral());
        assertEquals(TokenType.PLUS, tokens.get(4).getType());
        assertEquals(TokenType.NUMBER, tokens.get(5).getType());
        assertEquals(20.0, tokens.get(5).getLiteral());
        assertEquals(TokenType.SEMICOLON, tokens.get(6).getType());
        assertEquals(TokenType.EOF, tokens.get(7).getType());
    }

    @Test
    public void testStringTokens() {
        Lexer lexer = new Lexer("\"hello world\"");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(2, tokens.size());
        assertEquals(TokenType.STRING, tokens.get(0).getType());
        assertEquals("hello world", tokens.get(0).getLiteral());
    }
}
