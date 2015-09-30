package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.CodeAttribute;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 * @author Obicere
 */
public class jsr_w extends Instruction {

    private static final String MNEMONIC = "jsr_w";
    private static final int    OPCODE   = 0xc9;

    private final int branchbyte1;
    private final int branchbyte2;
    private final int branchbyte3;
    private final int branchbyte4;

    public jsr_w(final int branchbyte1, final int branchbyte2, final int branchbyte3, final int branchbyte4) {
        super(MNEMONIC, OPCODE);
        this.branchbyte1 = branchbyte1;
        this.branchbyte2 = branchbyte2;
        this.branchbyte3 = branchbyte3;
        this.branchbyte4 = branchbyte4;
    }

    public int getBranchbyte1() {
        return branchbyte1;
    }

    public int getBranchbyte2() {
        return branchbyte2;
    }

    public int getBranchbyte3() {
        return branchbyte3;
    }

    public int getBranchbyte4() {
        return branchbyte4;
    }

    public int getBranchOffset(){
        return (branchbyte1 << 24) | (branchbyte2 << 16) | (branchbyte3 << 8) | branchbyte4;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        super.model(builder);
        builder.tab();
        final CodeAttribute code = (CodeAttribute) builder.getProperty("code");
        final String line = code.getBlockName(getStart(), getBranchOffset());
        if (line == null) {
            builder.add(getBranchOffset());
        } else {
            builder.add(line);
        }
    }
}
