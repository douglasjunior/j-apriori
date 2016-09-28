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
     * @param objects
     */
    public void write(Object... record) throws IOException;

}
