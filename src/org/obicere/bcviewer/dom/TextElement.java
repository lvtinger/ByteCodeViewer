package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class TextElement extends Element {

    private String text;

    private int leftPad  = 0;
    private int rightPad = 0;

    private Attributes attributes = new Attributes();

    private final TextView view = new TextView(this);

    public TextElement(final String name) {
        this(name, null);
    }

    public TextElement(final String name, final String text) {
        super(name);
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
    }

    public void setAttributes(final Attributes attributes) {
        this.attributes = attributes;
    }

    public String getDisplayText() {
        final PaddingCache cache = PaddingCache.getPaddingCache();
        return cache.getPadding(getLeftPad()) + getText() + cache.getPadding(getRightPad());
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
        view.dispose();
    }

    public void setLeftPad(final int pad) {
        if (pad < 0) {
            throw new IllegalArgumentException("pad size cannot be less than 0.");
        }
        this.leftPad = pad;
        view.dispose();
    }

    public void setRightPad(final int pad) {
        if (pad < 0) {
            throw new IllegalArgumentException("pad size cannot be less than 0.");
        }
        this.rightPad = pad;
        view.dispose();
    }

    public int getLeftPad() {
        return leftPad;
    }

    public int getRightPad() {
        return rightPad;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Caret getCaret(final int x, final int y) {
        final int index = getView().getCaretIndex(x, y);
        if (index == -1) {
            return null;
        } else {
            return new Caret(this, index);
        }
    }

    @Override
    public TextView getView() {
        return view;
    }

    @Override
    public void writeSelf(final DocumentContent content) {
        content.write(getText());
    }
}
