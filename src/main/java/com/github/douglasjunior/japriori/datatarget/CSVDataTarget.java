/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.douglasjunior.japriori.datatarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author Douglas
 */
public class CSVDataTarget implements DataTarget {

    private final File file;
    private final char delimiter;
    private final Charset charset;
    private final Object[] headers;

    private CSVFormat csvFormat;
    private CSVPrinter csvPrinter;

    public CSVDataTarget(String path, char delimiter, Charset charset, Object[] headers) throws IOException {
        this(new File(path), delimiter, charset, headers);
    }

    public CSVDataTarget(File file, char delimiter, Charset charset, Object[] headers) throws IOException {
        this.file = file;
        this.delimiter = delimiter;
        this.charset = charset;
        this.headers = headers;
        open();
    }

    private void open() throws IOException {
        csvFormat = CSVFormat.DEFAULT
                .withDelimiter(delimiter)
                .withIgnoreEmptyLines()
                .withFirstRecordAsHeader()
                .withSkipHeaderRecord();

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
        csvPrinter = csvFormat.print(printWriter);
        csvPrinter.printRecord(headers);
    }

    @Override
    public void write(Object... objects) throws IOException {
        csvPrinter.printRecord(objects);
    }

    @Override
    public void close() throws IOException {
        csvPrinter.close();
    }

}
