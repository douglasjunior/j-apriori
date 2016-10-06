/*
Author: Douglas Nassif Roma Junior <nassifrroma@gmail.com>
Repository: https://github.com/douglasjunior/j-apriori
 */
package com.github.douglasjunior.japriori;

import com.github.douglasjunior.japriori.algorithm.AprioriAlgorithm;
import com.github.douglasjunior.japriori.datasource.CSVDataSource;
import com.github.douglasjunior.japriori.datatarget.CSVDataTarget;
import java.io.IOException;
import java.nio.charset.Charset;
import com.github.douglasjunior.japriori.datasource.DataSource;
import com.github.douglasjunior.japriori.datatarget.DataTarget;

/**
 * Classe responsável apenas por instanciar e executar o algorítmo
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

    /**
     * Inicialmente é preciso configurar as constantes de acordo com os
     * parâmetros desejados, como o valor de suporte e confiança, delimitador do
     * CSV, arquivo de entrada e saída, etc...
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        try (DataSource dataSource = new CSVDataSource(DATASOURCE_PATH, DELIMITER, Charset.forName(CHARSET));
                DataTarget dataTarget = new CSVDataTarget(DATATARGET_PATH, DELIMITER)) {

            AprioriAlgorithm apriori = new AprioriAlgorithm(dataSource, dataTarget, SUPPORT, CONFIDENCE);
            apriori.execute();

        }

    }
}
