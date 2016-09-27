package com.github.douglasjunior.japriori.datasource;

import java.io.Closeable;

/**
 *
 * @author Douglas
 */
public interface JADataSource extends Closeable {
    
    /**
     * Deve retornar um array com os itens do itemset.
     * @return 
     */
    public Object[] nextItemset();
        
    public boolean hasNext();
}
