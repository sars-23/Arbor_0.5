package com.arbor.gui;

import com.arbor.ast.Stmt;
import com.arbor.error.ArborError;
import com.arbor.lexer.Lexer;
import com.arbor.lexer.Token;
import com.arbor.parser.Parser;
import com.arbor.runtime.Interpreter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.List;

public class ArborIDE extends JFrame {
    private JTextArea outputArea;
    private JTextArea inputArea;
    private Interpreter interpreter;

    public ArborIDE() {
        super("Arbor IDE (v0.5)");
        interpreter = new Interpreter();

        setupUI();
        redirectSystemStreams();

        System.out.println("Arbor Interactive Shell (v0.5)");
        System.out.println("Type your code below and press Shift+Enter to run.");
        System.out.println("---------------------------------------------------");
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Output Area (Top)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBackground(new Color(30, 30, 30));
        outputArea.setForeground(new Color(220, 220, 220));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input Area (Bottom)
        inputArea = new JTextArea(5, 50);
        inputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputArea.setBackground(new Color(45, 45, 45));
        inputArea.setForeground(Color.WHITE);
        inputArea.setCaretColor(Color.WHITE);

        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
                    e.consume();
                    String code = inputArea.getText();
                    inputArea.setText("");
                    System.out.println(">>> " + code.trim());
                    executeCode(code);
                }
            }
        });

        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        add(inputScrollPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void redirectSystemStreams() {
        CustomOutputStream out = new CustomOutputStream(outputArea);
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    private void executeCode(String source) {
        if (source.trim().isEmpty()) return;

        try {
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.scanTokens();

            Parser parser = new Parser(tokens);
            List<Stmt> statements = parser.parse();

            if (statements != null && !statements.isEmpty()) {
                interpreter.interpret(statements);
            }
        } catch (ArborError error) {
            System.err.println(error.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            ArborIDE ide = new ArborIDE();
            ide.setVisible(true);
        });
    }
}
