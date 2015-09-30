package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 * @author Obicere
 */
public class StringElementValue extends ElementValue {

    private static final int TAG = 's';

    private final int constantValueIndex;

    public StringElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        final String constant = builder.getConstantPool().getAsString(constantValueIndex);
        builder.addString(constant);
    }
}
