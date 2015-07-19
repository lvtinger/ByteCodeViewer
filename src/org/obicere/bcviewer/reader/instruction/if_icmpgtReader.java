package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.if_icmpgt;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class if_icmpgtReader implements Reader<if_icmpgt> {

    @Override
    public if_icmpgt read(final IndexedDataInputStream input) throws IOException {
        return new if_icmpgt(input.readUnsignedByte(), input.readUnsignedByte());
    }
}