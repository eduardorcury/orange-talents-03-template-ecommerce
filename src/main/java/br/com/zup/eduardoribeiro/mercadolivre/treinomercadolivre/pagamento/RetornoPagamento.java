package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface RetornoPagamento {

    Transacao criaTransacao(@NotNull @Valid Compra compra);

}
