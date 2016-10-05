package com.github.douglasjunior.japriori.datatarget;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author Douglas
 */
public interface DataTarget extends Closeable {

    /**
     * Grava os objetos do resultado.
     *
     * @param record
     * @throws java.io.IOException
     */
    public void write(Object... record) throws IOException;

    public void reset() throws IOException;
}
