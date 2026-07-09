package com.arbor.cli;

import com.arbor.ast.Stmt;
import com.arbor.error.ArborError;
import com.arbor.gui.ArborIDE;
import com.arbor.lexer.Lexer;
import com.arbor.lexer.Token;
import com.arbor.parser.Parser;
import com.arbor.runtime.Interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Arbor {

    private static final Interpreter interpreter = new Interpreter();

    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                System.out.println("Usage: arbor [script]");
                System.exit(64);
            } else if (args.length == 1) {
                runFile(args[0]);
            } else {
                // If no arguments, launch the GUI IDE!
                ArborIDE.start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void run(String source) {
        try {
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.scanTokens();

            Parser parser = new Parser(tokens);
            List<Stmt> statements = parser.parse();

            // Stop if there was a syntax error.
            if (statements == null || statements.isEmpty()) return;

            interpreter.interpret(statements);

        } catch (ArborError error) {
            System.err.println(error.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
