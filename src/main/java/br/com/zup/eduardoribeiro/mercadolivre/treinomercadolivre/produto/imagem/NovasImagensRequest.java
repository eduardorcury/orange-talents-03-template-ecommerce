package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.imagem;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NovasImagensRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> imagens;

    public NovasImagensRequest(@NotNull @Size(min = 1) List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }
}
