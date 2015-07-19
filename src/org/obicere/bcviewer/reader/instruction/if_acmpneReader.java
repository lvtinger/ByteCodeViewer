package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.if_acmpne;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class if_acmpneReader implements Reader<if_acmpne> {

    @Override
    public if_acmpne read(final IndexedDataInputStream input) throws IOException {
        return new if_acmpne(input.readUnsignedByte(), input.readUnsignedByte());
    }
}