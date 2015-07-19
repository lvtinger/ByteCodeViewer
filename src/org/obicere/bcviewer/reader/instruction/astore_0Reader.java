package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.astore_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class astore_0Reader implements Reader<astore_0> {

    private final astore_0 instance = new astore_0();

    @Override
    public astore_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}