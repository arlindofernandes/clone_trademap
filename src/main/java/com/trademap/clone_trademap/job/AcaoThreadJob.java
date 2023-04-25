package com.trademap.clone_trademap.job;

import com.trademap.clone_trademap.modelo.AcaoFavorita;
import com.trademap.clone_trademap.service.impl.AcaoB3Service;
import com.trademap.clone_trademap.service.impl.AcaoFavoritaService;
import com.trademap.clone_trademap.utils.LogUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcaoThreadJob implements DisposableBean, Runnable {
    @Autowired
    private AcaoFavoritaService acaoFavoritaService;
   @Autowired
    private AcaoB3Service acaoB3Service;
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
