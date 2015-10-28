package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class ldc extends Instruction {

    private static final String MNEMONIC = "ldc";
    private static final int    OPCODE   = 0x12;

    private final int index;

    public ldc(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        super.model(builder);
        builder.tab();
        builder.getConstantPool().get(index).modelValue(builder);
    }
}
