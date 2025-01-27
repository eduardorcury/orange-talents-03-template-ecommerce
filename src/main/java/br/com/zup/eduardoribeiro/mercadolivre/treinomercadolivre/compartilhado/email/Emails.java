package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.pergunta.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
public class Emails {

    @Autowired
    private EnviadorDeEmail enviadorDeEmail;

    public void enviaEmailNovaPergunta(@NotNull @Valid Pergunta pergunta) {

        String corpo = String.format("Olá! Você tem uma nova pergunta no produto %s: %s",
                pergunta.getNomeProduto(), pergunta.getTitulo());
        String assunto = "Nova pergunta no seu produto";

        enviadorDeEmail.enviar(corpo, assunto, "Mercado Livre",
                "apimercadolivre@gmail.com", pergunta.getEmailUsuario());

    }

    public void enviaEmailNovaCompra(@NotNull @Valid Compra compra) {

        String corpo = String.format("Olá! Seu produto %s foi comprado!" + "A quantidade da compra foi %s."
                        + "\n Agora você tem %s desse produto restantes.",
                compra.getProduto().getNome(),
                compra.getQuantidade(),
                compra.getProduto().getQuantidade());
        String assunto = "Nova compra do seu produto";

        enviadorDeEmail.enviar(corpo, assunto, "Mercado Livre",
                "apimercadolivre@gmail.com", compra.getProduto().retornaEmailDoUsuario());

    }

    public void enviarEmailConfirmacaoPagamento(Compra compra) {

        String corpo = String.format("A sua compra do produto %s foi confirmada!",
                compra.getProduto().getNome());
        String assunto = "Compra confirmada";

        enviadorDeEmail.enviar(corpo, assunto, "Mercado Livre",
                "apimercadolivre@gmail.com", compra.getUsuario().getUsername());

    }

    public void enviarEmailFalhaPagamento(Compra compra) {

        String corpo = String.format("Seu pagamento não foi aceito :( Tente de novo aqui: " +
                        "localhost:8080/produtos/%s", compra.getProduto().getId().toString());
        String assunto = "Pagamento não foi aceito";

        enviadorDeEmail.enviar(corpo, assunto, "Mercado Livre",
                "apimercadolivre@gmail.com", compra.getUsuario().getUsername());

    }
}
