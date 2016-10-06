/*
Author: Douglas Nassif Roma Junior <nassifrroma@gmail.com>
Repository: https://github.com/douglasjunior/j-apriori
 */
package com.github.douglasjunior.japriori.datasource;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/**
 * Classe que representa uma fonte de dados para a execução do algoritmo. Essa
 * fonte pode ser um arquivo CSV, XML, banco de dados SQL e entre outros. Só
 * depende de existir uma implementação para cada tipo.
 *
 * @author Douglas
 */
public interface DataSource extends Closeable, Iterator<Object[]> {

    /**
     * Retorna um registro de atributos da fonte de dados em formato de Array de
     * Objetos.
     *
     * @return
     */
    @Override
    public Object[] next();

    @Override
    public boolean hasNext();

    /**
     * Reinicia a fonte de dados permitindo que a leitura se inicie do primeiro
     * registro novamente.
     *
     * @throws IOException
     */
    public void reset() throws IOException;
}
