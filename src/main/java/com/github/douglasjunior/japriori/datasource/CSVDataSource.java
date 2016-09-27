package com.github.douglasjunior.japriori.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Douglas
 */
public class CSVDataSource implements JADataSource {

    private File file;
    private CSVFormat csvFormat;
    private char delimiter;
    private Charset charset;
    private CSVParser csvParser;
    private Iterator<CSVRecord> iterator;

    public CSVDataSource(String path, char delimiter, Charset charset) throws IOException {
        this(new File(path), delimiter, charset);
    }

    public CSVDataSource(File file, char delimiter, Charset charset) throws IOException {
        System.out.println(file.getAbsolutePath());
        this.file = file;
        this.delimiter = delimiter;
        this.charset = charset;
        open();
    }

    private void open() throws IOException {
        csvFormat = CSVFormat.DEFAULT
                .withDelimiter(delimiter)
                .withIgnoreEmptyLines()
                .withSkipHeaderRecord();

        Reader in = new InputStreamReader(new FileInputStream(file), charset);
        csvParser = csvFormat.parse(in);
        iterator = csvParser.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object[] nextItemset() {
        CSVRecord record = iterator.next();
        Object[] objs = new Object[record.size()];
        for (int i = 0; i < record.size(); i++) {
            objs[i] = record.get(i);
        }
        return objs;
    }

    @Override
    public void close() throws IOException {
        csvParser.close();
    }

}
