package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compartilhado.email;

public interface EnviadorDeEmail {

    /**
     * @param corpo - corpo do e-mail
     * @param assunto - assunto do e-mail
     * @param nomeEmissor - nome do emissor do e-mail
     * @param emailEmissor - endereço de e-mail do emissor
     * @param emailReceptor - endereço de e-mail do receptor
     */
    void enviar(String corpo, String assunto, String nomeEmissor,
                String emailEmissor, String emailReceptor);

}
