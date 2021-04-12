package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosNovaCompra {

    @Autowired
    private EventosSucessoCompra eventosSucessoCompra;

    @Autowired
    private EventosFalhaCompra eventosFalhaCompra;

    public void processa(Compra compra) {
        if (compra.processadaComSucesso()) {
            eventosSucessoCompra.executaEventosDeSucesso(compra);
        } else {
            eventosFalhaCompra.executaEventosDeFalha(compra);
        }
    }

}
