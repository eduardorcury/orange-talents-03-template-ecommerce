package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email.Emails;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventosSucessoCompraImpl implements EventosSucessoCompra {

    @Autowired
    private Emails emails;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void executaEventosDeSucesso(Compra compra) {

        String urlNotasFiscais = String.format(
                "http://localhost:8080/notasfiscais?compraId=%s&usuarioId=%s",
                compra.getId().toString(), compra.getCompradorId().toString());
        String urlRanking = String.format(
                "http://localhost:8080/ranking?compraId=%s&vendedorId=%s",
                compra.getId().toString(), compra.getVendedorId().toString());

        restTemplate.getForEntity(urlNotasFiscais, String.class);
        restTemplate.getForEntity(urlRanking, String.class);

        emails.enviarEmailConfirmacaoPagamento(compra);
    }

}
