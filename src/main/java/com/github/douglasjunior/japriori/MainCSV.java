package com.github.douglasjunior.japriori;

import com.github.douglasjunior.japriori.datasource.CSVDataSource;
import com.github.douglasjunior.japriori.datatarget.CSVDataTarget;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import com.github.douglasjunior.japriori.datasource.DataSource;
import com.github.douglasjunior.japriori.datatarget.DataTarget;
import java.io.File;

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
        try (
                DataSource dataSource
                = new CSVDataSource(DATASET_PATH, DELIMITER, Charset.forName(CHARSET));
                DataTarget dataTarget
                = new CSVDataTarget(new File("dataset2.csv"), DELIMITER, Charset.forName(CHARSET), new String[]{"cid1", "cid2"})) {

            while (dataSource.hasNext()) {
                Object[] record = dataSource.next();
                dataTarget.write(record[1], record[3]);
                System.out.println(Arrays.toString(record));
            }
        }

        System.out.println(System.getProperty("java.io.tmpdir"));
    }
}
