package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping
public class ProcessaPagamentoController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EventosNovaCompra eventosNovaCompra;

    @PostMapping("/retorno-paypal/{id}")
    @Transactional
    public ResponseEntity<?> processaPagamentoPaypal(@PathVariable("id") Long compraId,
                                                     @RequestBody @Valid RetornoPagamentoPaypal pagamento) {
        return processa(compraId, pagamento);
    }

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public ResponseEntity<?> processaPagamentoPagseguro(@PathVariable("id") Long compraId,
                                                        @RequestBody @Valid RetornoPagamentoPagseguro pagamento) {
        return processa(compraId, pagamento);
    }

    private ResponseEntity<?> processa(Long compraId, RetornoPagamento pagamento) {

        Compra compra = entityManager.find(Compra.class, compraId);
        compra.adicionaTransacao(pagamento);
        entityManager.merge(compra);
        eventosNovaCompra.processa(compra);
        return ResponseEntity.ok().build();

    }
}
