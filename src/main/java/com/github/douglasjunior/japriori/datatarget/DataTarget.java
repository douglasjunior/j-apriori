package com.github.douglasjunior.japriori.datatarget;

import java.io.Closeable;
import java.io.IOException;

/**
 * Classe que representa o destino do resultado da execução do algoritmo. Esse
 * destino pode ser um arquivo CSV, XML, banco de dados SQL e entre outros. Só
 * depende de existir uma implementação para cada tipo.
 *
 * @author Douglas
 */
public interface DataTarget extends Closeable {

    /**
     * Grava os objetos do resultado como um registro no destino.
     *
     * @param record
     * @throws java.io.IOException
     */
    public void write(Object... record) throws IOException;

}
