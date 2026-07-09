package com.arbor.gui;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {
    private final JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        // Redirect data to the text area
        updateTextArea(String.valueOf((char) b));
    }

    @Override
    public void write(byte[] b, int off, int len) {
        updateTextArea(new String(b, off, len));
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(text);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }
}
