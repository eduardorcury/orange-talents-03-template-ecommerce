package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private UUID codigoDaCompra = UUID.randomUUID();

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gateway gateway;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal valorProduto;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Produto produto;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Usuario usuario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDaCompra statusDaCompra = StatusDaCompra.INICIADA;

    public Compra(@NotNull @Positive Integer quantidade,
                  @NotNull Gateway gateway,
                  @NotNull @Valid Produto produto,
                  @NotNull @Valid Usuario usuario) {
        this.quantidade = quantidade;
        this.gateway = gateway;
        this.valorProduto = produto.getValor();
        this.produto = produto;
        this.usuario = usuario;
    }

    public String retornaUrl() {
        return String.format("%s.com?%s=%s&redirectUrl=%s",
                this.gateway.name().toLowerCase(),
                this.gateway == Gateway.PAYPAL ? "buyerId" : "returnId",
                this.codigoDaCompra.toString(), "teste");
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

}
