package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.builders;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.categoria.Categoria;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Gateway;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento.RetornoPagamento;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento.RetornoPagamentoPagseguro;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class TestBuilders {

    public static class CompraFixture {

        private Compra compra;

        public CompraFixture(Compra compra) {
            this.compra = compra;
        }

        public Compra concluida() {
            RetornoPagamento sucesso = new RetornoPagamentoPagseguro("1", "SUCESSO");
            this.compra.adicionaTransacao(sucesso);
            return this.compra;
        }

        public Compra erro() {
            RetornoPagamento erro = new RetornoPagamentoPagseguro("1", "ERRO");
            this.compra.adicionaTransacao(erro);
            return this.compra;
        }

    }

    public static CompraFixture novaCompra() {

        Categoria categoria = new Categoria("categoria");
        Usuario usuario = new Usuario("usuario@gmail.com", "senhausuario");
        List<NovaCaracteristicaRequest> caracteristicasValidas = List.of(new NovaCaracteristicaRequest("Caracteristica 1", "Descrição 1"),
                new NovaCaracteristicaRequest("Caracteristica 2", "Descrição 2"),
                new NovaCaracteristicaRequest("Caracteristica 3", "Descrição 3"));
        Produto produto = new Produto("nome", BigDecimal.ONE, 5, "descrição",
                categoria, usuario, caracteristicasValidas);

        return new CompraFixture(new Compra(5, Gateway.PAYPAL, produto, usuario));

    }

}
