package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.fload_0;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class fload_0Reader implements Reader<fload_0> {

    private final fload_0 instance = new fload_0();

    @Override
    public fload_0 read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}