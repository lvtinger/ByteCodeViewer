package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.ifle;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;

/**
 * @author Obicere
 */
public class ifleReader implements Reader<ifle> {

    private final ifle instance = new ifle();

    @Override
    public ifle read(final IndexedDataInputStream input) throws IOException {
        return instance;
    }
}