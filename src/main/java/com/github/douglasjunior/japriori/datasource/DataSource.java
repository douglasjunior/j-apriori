package com.github.douglasjunior.japriori.datasource;

import java.io.Closeable;
import java.util.Iterator;

/**
 *
 * @author Douglas
 */
public interface DataSource extends Closeable, Iterator<Object[]> {

     /**
     * Deve retornar um array com os itens do itemset.
     * @return 
     */
    @Override
    public Object[] next();

    @Override
    public boolean hasNext();
}
