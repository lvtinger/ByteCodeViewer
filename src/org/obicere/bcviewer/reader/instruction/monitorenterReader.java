package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.monitorenter;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class monitorenterReader implements Reader<monitorenter> {

    private final monitorenter instance = new monitorenter();

    @Override
    public monitorenter read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}