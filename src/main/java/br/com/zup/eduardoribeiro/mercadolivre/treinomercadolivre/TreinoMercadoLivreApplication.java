package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TreinoMercadoLivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinoMercadoLivreApplication.class, args);
	}

}
