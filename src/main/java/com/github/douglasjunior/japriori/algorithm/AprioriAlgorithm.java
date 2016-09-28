package com.github.douglasjunior.japriori.algorithm;

import com.github.douglasjunior.japriori.datasource.DataSource;
import com.github.douglasjunior.japriori.datatarget.DataTarget;

/**
 *
 * @author Douglas
 */
public class AprioriAlgorithm extends Algorithm {

    private final DataSource dataSource;
    private final DataTarget dataTarget;

    public AprioriAlgorithm(DataSource dataSource, DataTarget dataTarget){
        this.dataSource = dataSource;
        this.dataTarget = dataTarget;
    }
    
    @Override
    public void compute() {
        
    }

}
