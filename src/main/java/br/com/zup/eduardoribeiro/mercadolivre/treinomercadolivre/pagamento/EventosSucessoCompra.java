package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;

public interface EventosSucessoCompra {

    void executaEventosDeSucesso(Compra compra);

}
