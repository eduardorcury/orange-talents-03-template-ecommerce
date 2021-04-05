package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Set;

public interface Uploader {

    Set<URL> envia(List<MultipartFile> imagens);
}
