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
import org.apache.commons.csv.QuoteMode;

/**
 *
 * @author Douglas
 */
public class CSVDataTarget implements DataTarget {

    private static final Object[] HEADERS = {"XY", "X", "sup", "conf"};

    private final File file;
    private final Charset charset;

    private CSVFormat csvFormat;
    private CSVPrinter csvPrinter;

    public CSVDataTarget(String path, char delimiter) throws IOException {
        this(new File(path), delimiter, Charset.defaultCharset());
    }

    public CSVDataTarget(String path, char delimiter, Charset charset) throws IOException {
        this(new File(path), delimiter, charset);
    }

    public CSVDataTarget(File file, char delimiter, Charset charset) throws IOException {
        this.file = file;
        this.charset = charset;
        this.csvFormat = CSVFormat.DEFAULT
                .withDelimiter(delimiter)
                .withIgnoreEmptyLines()
                .withEscape('\\')
                .withQuoteMode(QuoteMode.NONE)
                .withSkipHeaderRecord();
        open();
    }

    private void open() throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
        csvPrinter = csvFormat.print(printWriter);
        csvPrinter.printRecord(HEADERS);
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
