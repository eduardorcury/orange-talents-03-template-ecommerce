package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@Profile("dev")
public class UploaderDev implements Uploader {

    @Override
    public Set<URL> envia(List<MultipartFile> imagens) {

        Set<URL> links = new HashSet<>();
        imagens.forEach(imagem -> {
                    int posicao = imagem.getOriginalFilename().lastIndexOf(".");
                    String nome = (posicao == -1)
                            ? imagem.getOriginalFilename() + "-" + UUID.randomUUID().toString()
                            : imagem.getOriginalFilename().substring(0, posicao) + "-"
                                + UUID.randomUUID().toString() + imagem.getOriginalFilename().substring(posicao);

                    try {
                        links.add(new URL("http://bucket.io/" + nome));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

        });

        return links;

    }
}
