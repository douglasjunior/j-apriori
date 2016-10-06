/*
Author: Douglas Nassif Roma Junior <nassifrroma@gmail.com>
Repository: https://github.com/douglasjunior/j-apriori
 */
package com.github.douglasjunior.japriori;

import com.github.douglasjunior.japriori.algorithm.Algorithm;
import com.github.douglasjunior.japriori.algorithm.AprioriAlgorithm;
import com.github.douglasjunior.japriori.datasource.CSVDataSource;
import com.github.douglasjunior.japriori.datasource.DataSource;
import com.github.douglasjunior.japriori.datatarget.CSVDataTarget;
import com.github.douglasjunior.japriori.datatarget.DataTarget;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * @author Douglas
 */
public class MainCSVAsync {

    private static final float SUPPORT = 40;
    private static final float CONFIDENCE = 60;

    private static final char DELIMITER = ';';
    private static final String CHARSET = "UTF-8";
    private static final String DATASOURCE_PATH = "dataset-input.csv";
    private static final String DATATARGET_PATH = "dataset-output.csv";

    /**
     * Executa o algoritmo de forma assíncrona.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DataSource dataSource = new CSVDataSource(DATASOURCE_PATH, DELIMITER, Charset.forName(CHARSET));
        DataTarget dataTarget = new CSVDataTarget(DATATARGET_PATH, DELIMITER);

        AprioriAlgorithm apriori = new AprioriAlgorithm(dataSource, dataTarget, SUPPORT, CONFIDENCE);
        apriori.executeAsync(new Algorithm.OnComputeFinishListener() {
            @Override
            public void onSuccess() {
                System.out.println("Success.");
                try {
                    dataSource.close();
                    dataTarget.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.toString());
                try {
                    dataSource.close();
                    dataTarget.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        System.out.println("Started async.");
    }
}
