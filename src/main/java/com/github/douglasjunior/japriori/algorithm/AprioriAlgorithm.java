package com.github.douglasjunior.japriori.algorithm;

import com.github.douglasjunior.japriori.datasource.DataSource;
import com.github.douglasjunior.japriori.datatarget.DataTarget;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Douglas
 */
public class AprioriAlgorithm extends Algorithm {

    private final DataSource dataSource;
    private final DataTarget dataTarget;
    private final float support;
    private final float confidence;

    // quantidade mínima de suporte
    private int minSup = 0;
    // porcentagem mínima de confiança
    private float minConf = 0;
    // total de transações
    private int totalTransaction = 0;

    // coleção de iterações de K, exemplo:
    // 
    // BD:
    //
    // Pão;Leite
    // Pão;Fralda;Cerveja;Ovos
    // Leite;Fralda;Cerveja;Coca
    // Pão;Leite;Fralda;Cerveja
    // Pão;Leite;Fralda;Coca
    //
    // Resultado:
    //
    // k1 = {[Fralda]=4, [Coca]=2, [Pão]=4, [Cerveja]=3, [Ovos]=1, [Leite]=4}
    // k2 = {[Fralda, Leite]=3, [Pão, Leite]=3, [Fralda, Pão]=3, [Fralda, Cerveja]=3}}
    //
    private Map<Integer, Map<Set, Integer>> collection = new HashMap<>();

    public AprioriAlgorithm(DataSource dataSource, DataTarget dataTarget, float support, float confidence) {
        this.dataSource = dataSource;
        this.dataTarget = dataTarget;
        this.support = support;
        this.confidence = confidence;
        this.minConf = confidence / 100f;
    }

    @Override
    public void execute() throws IOException {
        selectFirstItemset();
        selectCollection();
        calculeConf();

        dataSource.close();
        dataTarget.close();

        System.out.println("Completed.");
    }

    /**
     * Calcula o suporte para K = 1 e soma a quantidade total de transações
     *
     * @throws IOException
     */
    private void selectFirstItemset() throws IOException {
        Map<Set, Integer> firstItemset = new HashMap<>();

        while (dataSource.hasNext()) {
            totalTransaction++;
            Object[] record = dataSource.next();
            for (Object attr : record) {
                Set itemSet = new HashSet(Arrays.asList(attr));
                Integer count = firstItemset.get(itemSet);
                count = count == null ? 1 : count + 1;
                firstItemset.put(itemSet, count);
            }
        }
        dataSource.reset();

        minSup = Math.round(totalTransaction * (support / 100f));

        // no final remove registros com suporte baixo, pois na primeira iteração ainda não se sabe o total de registros
        for (Iterator<Set> it = firstItemset.keySet().iterator(); it.hasNext();) {
            Set itemSet = it.next();
            if (firstItemset.get(itemSet) < minSup) {
                it.remove();
            }
        }

        // armazena o itemset de K = 1
        collection.put(1, firstItemset);
    }

    /**
     * Seguindo o algorítmo, incrementa o valor de K formando novos conjuntos e
     * calculando o suporte dos mesmos, até que mais nenhum conjunto satisfaça o
     * suporte mínimo.
     *
     * @throws IOException
     */
    private void selectCollection() throws IOException {
        Map<Set, Integer> firstItemset = collection.get(1); // primeiro (k = 1)
        Map<Set, Integer> lastItemset;
        Map<Set, Integer> currentItemset;

        int k = 2;

        while (true) {
            lastItemset = collection.get(k - 1); // anterior (last = k - 1)
            currentItemset = new HashMap<>(); // atual

            for (Set last : lastItemset.keySet()) {
                for (Set first : firstItemset.keySet()) {

                    if (!last.containsAll(first)) {
                        Set kItem = new HashSet(last);
                        kItem.addAll(first);

                        int count = 0;
                        while (dataSource.hasNext()) {
                            List record = Arrays.asList(dataSource.next());
                            boolean contains = true;
                            for (Object item : kItem) {
                                if (!record.contains(item)) {
                                    contains = false;
                                    break;
                                }
                            }
                            count = contains ? count + 1 : count;
                        }
                        dataSource.reset();

                        // compara o suporte do novo itemset
                        if (count >= minSup) {
                            currentItemset.put(kItem, count);
                        }
                    }

                }
            }

            // algorítmo finaliza quando o novo nível de K não possui conjuntos que satisfaçam o minSup
            if (currentItemset.isEmpty()) {
                break;
            }

            collection.put(k, currentItemset);
            k++;
        }
    }

    /**
     * Calcula a confiança para toda a coleção, iniciando do nível K = 2 até K =
     * n
     *
     * Conf = Registros com X e Y / Registros com X
     *
     * @throws IOException
     */
    private void calculeConf() throws IOException {
        Map<Set, Integer> firstItemset = collection.get(1);
        for (int k = 2; k <= collection.size(); k++) {

            Map<Set, Integer> currentItemset = collection.get(k);

            for (Set current : currentItemset.keySet()) {
                float countXY = currentItemset.get(current);
                float sup = countXY / totalTransaction;
                for (Object item : current) {
                    float countX = firstItemset.get(new HashSet<>(Arrays.asList(item)));
                    float conf = countXY / countX;
                    if (conf >= minConf) {
                        dataTarget.write(toString(current), item, sup, conf);
                    }
                }
            }
        }
    }

    /**
     * Converte um objeto para String para ficar apresentável na saída do CSV.
     *
     * @param object
     * @return
     */
    private String toString(Object object) {
        if (object instanceof Collection) {
            Collection coll = (Collection) object;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (Object obj : coll) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append(toString(obj));
            }
            sb.append("]");
            return sb.toString();
        }
        return object.toString();
    }

}
