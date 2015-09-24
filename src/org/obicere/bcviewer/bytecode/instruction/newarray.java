package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterKeywordElement;

/**
 * @author Obicere
 */
public class newarray extends Instruction {

    private static final String MNEMONIC = "newarray";
    private static final int    OPCODE   = 0xbc;

    private final int atype;

    public newarray(final int atype) {
        super(MNEMONIC, OPCODE);
        this.atype = atype;
    }

    public int getAtype() {
        return atype;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterKeywordElement("type", getTypeOfAType(), builder));
    }

    private String getTypeOfAType() {
        // 6.5.newarray Table 6.1
        switch (atype) {
            case 4:
                return "boolean";
            case 5:
                return "char";
            case 6:
                return "float";
            case 7:
                return "double";
            case 8:
                return "byte";
            case 9:
                return "short";
            case 10:
                return "int";
            case 11:
                return "long";
            default:
                throw new IllegalArgumentException("illegal aType value.");
        }
    }
}
