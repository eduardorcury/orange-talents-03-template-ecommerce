package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeController {

    @GetMapping("/notasfiscais")
    public ResponseEntity<String> notificaSetorNotasFiscais(@RequestParam(name = "compraId", required = true) Long compraId,
                                                            @RequestParam(name = "usuarioId", required = true) Long usuarioId) {

        return ResponseEntity.ok("Setor de notas fiscais notificado. Id da compra: "
                + compraId + ", Id do usuário" + usuarioId);

    }

    @GetMapping("/ranking")
    public ResponseEntity<String> notificaSetorRanking(@RequestParam(name = "compraId", required = true) Long compraId,
                                                       @RequestParam(name = "vendedorId", required = true) Long vendedorId) {

        return ResponseEntity.ok("Setor de ranking de vendedoresnotificado. Id da compra: "
                + compraId + ", Id do vendedor" + vendedorId);

    }

}
