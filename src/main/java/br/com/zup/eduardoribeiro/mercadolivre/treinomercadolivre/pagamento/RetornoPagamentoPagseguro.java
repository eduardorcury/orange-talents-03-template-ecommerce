package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RetornoPagamentoPagseguro implements RetornoPagamento {

    @NotBlank
    private String transacaoId;

    @NotBlank
    @Pattern(regexp = "SUCESSO|ERRO", message = "Valores possíveis são SUCESSO ou ERRO")
    private String status;

    public RetornoPagamentoPagseguro(@NotBlank String transacaoId,
                                     @NotBlank @Pattern(regexp = "SUCESSO|ERRO",
                                      message = "Valores possíveis são SUCESSO ou ERRO")
                                      String status) {
        this.transacaoId = transacaoId;
        this.status = status;
    }

    @Override
    public Transacao criaTransacao(@NotNull @Valid Compra compra) {

        if (status.equals("SUCESSO")) {
            return new Transacao(compra, this.transacaoId, true);
        } else {
            return new Transacao(compra, this.transacaoId, false);
        }

    }

}