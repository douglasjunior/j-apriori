package com.github.douglasjunior.japriori;

import com.github.douglasjunior.japriori.datasource.CSVDataSource;
import com.github.douglasjunior.japriori.datasource.JADataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 *
 * @author Douglas
 */
public class MainCSV {

    private static final int COL_X = 1;
    private static final int COL_Y = 3;
    
    private static final String DATASET_PATH = "dataset.csv";
    private static final char DELIMITER = ';';
    private static final String CHARSET = "UTF-8";
    
    public static void main(String[] args) throws IOException {
        try (JADataSource dataSource
                = new CSVDataSource(DATASET_PATH, DELIMITER, Charset.forName(CHARSET));) {

            while (dataSource.hasNext()) {
                Object[] record = dataSource.nextItemset();
                System.out.println(Arrays.toString(record));
            }
        }
    }
}
