package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.pergunta;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email.EnviadorDeEmail;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class EnviadorEmailNovaPergunta implements EnviadorDeEmail {

    @Override
    public void enviar(String corpo, String assunto, String nomeEmissor,
                       String emailEmissor, String emailReceptor) {

        System.out.println("Email: " + corpo);
        System.out.println("Assunto: " + assunto);
        System.out.println("Nome do Emissor " + nomeEmissor);
        System.out.println("Email do Emissor " + emailEmissor);
        System.out.println("Email do receptor " + emailReceptor);

    }
}
