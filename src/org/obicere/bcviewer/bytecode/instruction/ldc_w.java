package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 * @author Obicere
 */
public class ldc_w extends Instruction {

    private static final String MNEMONIC = "ldc_w";
    private static final int    OPCODE   = 0x13;

    private final int indexbyte1;
    private final int indexbyte2;

    public ldc_w(final int indexbyte1, final int indexbyte2) {
        super(MNEMONIC, OPCODE);
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        return builder.toString();
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        super.model(builder);
        builder.tab();
        builder.getConstantPool().get(getIndex()).modelValue(builder);
    }
}
