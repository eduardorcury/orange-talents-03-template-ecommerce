package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email.Emails;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventosFalhaCompraImpl implements EventosFalhaCompra {

    @Autowired
    private Emails emails;

    @Override
    public void executaEventosDeFalha(Compra compra) {
        emails.enviarEmailFalhaPagamento(compra);
    }
}
