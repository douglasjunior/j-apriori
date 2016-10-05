package com.github.douglasjunior.japriori.algorithm;

import java.io.IOException;

/**
 *
 * @author Douglas
 */
public abstract class Algorithm {

    public abstract void compute() throws IOException;

    /**
     * Executa o algorítmo em segundo plano e avisa o Listener quando houver
     * sucesso ou falha.
     *
     * @param onComputeFinishListener
     */
    public void computeAsync(OnComputeFinishListener onComputeFinishListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    // executa o algorítmo em segundo plano.
                    compute();
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

    public static interface OnComputeFinishListener {

        public void onSuccess();

        public void onError(Throwable t);
    }

}
