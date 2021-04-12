package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class RetornoPagamentoPaypal implements RetornoPagamento {

    @NotBlank
    private String transacaoId;

    @NotNull
    @Min(value = 0, message = "Valores possíveis são 0 (falha) e 1 (sucesso)")
    @Max(value = 1, message = "Valores possíveis são 0 (falha) e 1 (sucesso)")
    private Integer status;

    public RetornoPagamentoPaypal(@NotBlank String transacaoId,
                                  @NotNull
                                  @Min(value = 0, message = "Valores possíveis são 0 (falha) e 1 (sucesso)")
                                  @Max(value = 1, message = "Valores possíveis são 0 (falha) e 1 (sucesso)")
                                          Integer status) {
        this.transacaoId = transacaoId;
        this.status = status;
    }

    @Override
    public Transacao criaTransacao(@NotNull @Valid Compra compra) {

        if (status == 1) {
            return new Transacao(compra, this.transacaoId, true);
        } else {
            return new Transacao(compra, this.transacaoId, false);
        }

    }

}
