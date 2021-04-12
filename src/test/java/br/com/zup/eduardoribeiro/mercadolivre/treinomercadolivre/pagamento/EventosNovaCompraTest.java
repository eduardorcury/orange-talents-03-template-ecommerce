package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.builders.TestBuilders;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EventosNovaCompraTest {

    @Test
    void testaEventosSucesso() {

        EventosSucessoCompra eventosSucessoCompra = mock(EventosSucessoCompra.class);
        Compra compra = TestBuilders.novaCompra().concluida();

        EventosNovaCompra eventosNovaCompra = new EventosNovaCompra();
        ReflectionTestUtils.setField(eventosNovaCompra, "eventosSucessoCompra", eventosSucessoCompra);

        eventosNovaCompra.processa(compra);
        verify(eventosSucessoCompra).executaEventosDeSucesso(compra);


    }

    @Test
    void testaEventosFalha() {

        EventosFalhaCompra eventosFalhaCompra = mock(EventosFalhaCompra.class);
        Compra compra = TestBuilders.novaCompra().erro();

        EventosNovaCompra eventosNovaCompra = new EventosNovaCompra();
        ReflectionTestUtils.setField(eventosNovaCompra, "eventosFalhaCompra", eventosFalhaCompra);

        eventosNovaCompra.processa(compra);
        verify(eventosFalhaCompra).executaEventosDeFalha(compra);

    }
}