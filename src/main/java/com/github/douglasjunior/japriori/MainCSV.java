package com.github.douglasjunior.japriori;

import com.github.douglasjunior.japriori.algorithm.AprioriAlgorithm;
import com.github.douglasjunior.japriori.datasource.CSVDataSource;
import com.github.douglasjunior.japriori.datatarget.CSVDataTarget;
import java.io.IOException;
import java.nio.charset.Charset;
import com.github.douglasjunior.japriori.datasource.DataSource;
import com.github.douglasjunior.japriori.datatarget.DataTarget;
import java.io.File;

/**
 *
 * @author Douglas
 */
public class MainCSV {

    private static final float SUPPORT = 40;
    private static final float CONFIDENCE = 60;

    private static final char DELIMITER = ';';
    private static final String CHARSET = "UTF-8";
    private static final String DATASOURCE_PATH = "dataset-input.csv";
    private static final String DATATARGET_PATH = "dataset-output.csv";
    private static final String[] OUTPUT_HEADERS = {"XY", "X", "sup", "conf"};

    public static void main(String[] args) throws IOException {
        try (
                DataSource dataSource
                = new CSVDataSource(DATASOURCE_PATH, DELIMITER, Charset.forName(CHARSET));
                DataTarget dataTarget
                = new CSVDataTarget(new File(DATATARGET_PATH), DELIMITER, Charset.forName(CHARSET), OUTPUT_HEADERS)) {

            AprioriAlgorithm apriori = new AprioriAlgorithm(dataSource, dataTarget, SUPPORT, CONFIDENCE);
            apriori.compute();
        }

    }
}
