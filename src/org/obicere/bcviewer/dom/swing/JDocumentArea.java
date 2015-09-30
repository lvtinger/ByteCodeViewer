package org.obicere.bcviewer.dom.swing;

import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.Line;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.swing.ui.DocumentAreaUI;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class JDocumentArea extends JComponent {

    private static final String uiClassID = "DocumentAreaUI";

    private QuickWidthFont font;

    private final ArrayList<Block> content = new ArrayList<>();

    private boolean thinCarets = true;

    private final Caret caret     = new Caret(this);
    private final Caret dropCaret = new Caret(this);

    static {
        UIManager.put(uiClassID, DocumentAreaUI.class.getName());
    }

    public JDocumentArea() {
        updateUI();
    }

    @Override
    public void updateUI() {
        setUI(UIManager.getUI(this));
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public Caret getCaret() {
        return caret;
    }

    public Caret getDropCaret() {
        return dropCaret;
    }

    public void scrollToCaret() {
        final JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
        if (scrollPane == null) {
            return;
        }

        final int fontHeight = font.getFixedHeight();
        final int fontWidth = font.getFixedWidth();
        final Rectangle caretRectangle = new Rectangle(caret.getColumn() * fontWidth, caret.getRow() * fontHeight, 1, fontHeight);
        scrollPane.getViewport().scrollRectToVisible(caretRectangle);
        revalidate();
        repaint();
    }

    public List<Block> getBlocks() {
        return content;
    }

    public boolean addBlock(final Block block) {
        if (block == null) {
            throw new NullPointerException("block must be non-null");
        }
        final int start;
        if (content.isEmpty()) {
            start = 0;
        } else {
            start = content.get(content.size() - 1).getLineEnd();
        }
        block.setLineStart(start);
        return content.add(block);
    }

    public Line getLine(final int count) {
        final Block containing = getBlockContaining(count);
        final int offset = count - containing.getLineStart();
        return containing.getLine(offset);
    }

    public List<Line> getLines(final int start, final int end) {
        if (end < start) {
            throw new IllegalArgumentException("end must be >= start");
        }
        final ArrayList<Line> lines = new ArrayList<>(end - start);
        if (start == end) {
            return lines;
        }
        final int startBlockIndex = getIndexOfBlockContaining(start);
        final int endBlockIndex = getIndexOfBlockContaining(end);

        if (startBlockIndex == endBlockIndex) {
            final Block block = content.get(startBlockIndex);

            if (block.isVisible()) {
                final int endOffset = end - block.getLineStart();
                final int startOffset = start - block.getLineStart();
                lines.addAll(block.getLines(startOffset, endOffset));
            }
            return lines;
        } else {
            final Block firstBlock = content.get(startBlockIndex);
            if (firstBlock.isVisible()) {
                final int startOffset = start - firstBlock.getLineStart();
                lines.addAll(firstBlock.getLines(startOffset, firstBlock.getLineCount()));
            }

            for (int index = startBlockIndex + 1; index < endBlockIndex; index++) {
                final Block block = content.get(index);
                if (block.isVisible()) {
                    lines.addAll(block.getLines());
                }
            }

            final Block lastBlock = content.get(endBlockIndex);
            if (lastBlock.isVisible()) {
                final int lastBlockEnd = (end - lastBlock.getLineStart());
                lines.addAll(lastBlock.getLines(0, lastBlockEnd));
            }
            return lines;
        }
    }

    public void setBlockVisible(final Block block, final boolean visible) {
        final int index = content.indexOf(block);
        if (index < 0) {
            return;
        }
        block.setVisible(visible);
        final int delta = visible ? block.getLineCount() : -block.getLineCount();
        for (int i = index + 1; i < content.size(); i++) {
            final Block next = content.get(i);
            next.setLineStart(next.getLineStart() + delta);
        }
    }

    private int getIndexOfBlockContaining(int lineNumber) {
        for (int i = 0; i < content.size(); i++) {
            final Block block = content.get(i);
            if (block.isVisible()) {
                lineNumber -= block.getLineCount();
                if (lineNumber <= 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Block getBlockContaining(int lineNumber) {
        if (lineNumber < 0) {
            return null;
        }
        for (final Block block : content) {
            if (block.isVisible()) {
                lineNumber -= block.getLineCount();
                if (lineNumber <= 0) {
                    return block;
                }
            }
        }
        return null;
    }

    public int getLineCount() {
        int lines = 0;
        for (final Block block : content) {
            if (block.isVisible()) {
                lines += block.getLineCount();
            }
        }
        return lines;
    }

    public int getMaxLineLength() {
        int max = 0;
        for (final Block block : content) {
            if (block.isVisible()) {
                final int nextLength = block.getMaxLineLength();
                if (max < nextLength) {
                    max = nextLength;
                }
            }
        }
        return max;
    }

    @Override
    public void setFont(final Font font) {
        if (!(font instanceof QuickWidthFont)) {
            throw new IllegalArgumentException("only supported fonts for the JDocumentArea are QuickWidthFonts");
        }
        final QuickWidthFont qwFont = (QuickWidthFont) font;
        if (!qwFont.isFixedWidth()) {
            throw new IllegalArgumentException("only fixed width fonts are supported");
        }
        final Font oldFont = this.font;
        firePropertyChange("font", oldFont, font);

        this.font = qwFont;
    }

    @Override
    public Font getFont() {
        return font;
    }

    public boolean isThinCarets() {
        return thinCarets;
    }

    public void setThinCarets(final boolean thinCarets) {
        final boolean old = this.thinCarets;
        if (old == thinCarets) {
            return;
        }
        this.thinCarets = thinCarets;
        firePropertyChange("thinCarets", old, thinCarets);
    }
}
