package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;

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
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        return builder.toString();
    }
}
