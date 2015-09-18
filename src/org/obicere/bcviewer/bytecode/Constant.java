package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.Modeler;

/**
 * @author Obicere
 */
public abstract class Constant extends BytecodeElement {

    private final int tag;

    public Constant(final int tag) {
        this.tag = tag;
    }

    public final int getTag() {
        return tag;
    }

    @Override
    public String getIdentifier() {
        return "constant" + getStart();
    }
}
