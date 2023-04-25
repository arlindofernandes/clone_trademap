package com.trademap.clone_trademap.job;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcaoThreadJob implements DisposableBean, Runnable {


    private Thread thread;
    private boolean someCondition;

    AcaoThreadJob() {
        this.thread = new Thread(this);
        this.thread.start();
        someCondition = true;
    }

    @Override
    public void run() {
        while(someCondition){
            try {
                Thread.sleep(10000);
                // Poderia ser consultado em um banco em memória para ser mais rápido
                List<AcaoFavorita> listaAcoes = acaoFavoritaService.listarSemDuplicidade();

                for (AcaoFavorita acaoFavorita : listaAcoes) {
                    acaoB3Service.atualizarValorAcao(acaoFavorita.getCodigo());
                }
            } catch (InterruptedException e) {
                LogUtil.error(e);
            }

        }
    }

    @Override
    public void destroy() throws Exception {
        someCondition = false;

    }
}
