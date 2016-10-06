package com.github.douglasjunior.japriori.algorithm;

import java.io.IOException;

/**
 * Classe que representa como deve ser a assinatura da implementação de um
 * algoritmo que receba uma entrada e devolva uma saída.
 *
 * @author Douglas
 */
public abstract class Algorithm {

    public abstract void execute() throws IOException;

    /**
     * Executa o algorítmo em segundo plano e avisa o Listener quando houver
     * sucesso ou falha.
     *
     * @param onComputeFinishListener
     */
    public void executeAsync(OnComputeFinishListener onComputeFinishListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    // executa o algorítmo em segundo plano.
                    execute();
                    if (onComputeFinishListener != null) {
                        onComputeFinishListener.onSuccess();
                    }
                } catch (Throwable t) {
                    if (onComputeFinishListener != null) {
                        onComputeFinishListener.onError(t);
                    }
                }
            }
        }.start();
    }

    /**
     * Classe ouvinte que recebe um evento ao término da execução do algorítmo.
     */
    public static interface OnComputeFinishListener {

        public void onSuccess();

        public void onError(Throwable t);
    }

}
