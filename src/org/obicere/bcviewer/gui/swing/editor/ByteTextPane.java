package org.obicere.bcviewer.gui.swing.editor;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Obicere
 */
public class ByteTextPane extends JTextPane {

    private byte[] bytes;

    public ByteTextPane() {
        setEditorKit(new ByteEditorKit());
        setBytes(new byte[]{1, 2, 3});
        setEditable(false);
    }

    /*@Override
    public Dimension getPreferredSize(){
        return new Dimension(400, 600);
    }*/

    @Override
    public void setText(final String text) {
        if (text == null) {
            throw new NullPointerException("Given text cannot be null.");
        }
        setBytes(text.getBytes());
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(final byte[] bytes) {
        try {
            this.bytes = bytes;
            final Document document = getDocument();
            document.remove(0, document.getLength());
            if (bytes == null || bytes.length == 0) {
                return;
            }
            final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            final EditorKit kit = getEditorKit();
            kit.read(input, getDocument(), 0);
        } catch (final IOException | BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth(){
        return getUI().getPreferredSize(this).width <= getParent().getSize().width;
    }

    @Override
    public boolean getScrollableTracksViewportHeight(){
        return getUI().getPreferredSize(this).height <= getParent().getSize().height;
    }
}
