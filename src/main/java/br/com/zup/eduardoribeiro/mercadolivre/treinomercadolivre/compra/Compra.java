package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento.RetornoPagamento;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento.Transacao;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.produto.Produto;
import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    public Compra() {

    }

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

    public String geraUrlDaCompra() {
        return String.format("%s.com?%s=%s&redirectUrl=/retorno-%s/%s",
                this.gateway.name().toLowerCase(),
                this.gateway == Gateway.PAYPAL ? "buyerId" : "returnId",
                this.codigoDaCompra.toString(),
                this.gateway.name().toLowerCase(),
                this.id.toString());
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Long getCompradorId() {
        return this.usuario.getId();
    }

    public Long getVendedorId() {
        return this.produto.retornaUsuarioId();
    }

    public void adicionaTransacao(@NotNull @Valid RetornoPagamento pagamento) {

        Transacao transacao = pagamento.criaTransacao(this);
        Assert.isTrue(!this.transacoes.contains(transacao), "Essa transação já existe nessa compra");
        Assert.isTrue(transacoesConcluidasComSucesso().isEmpty(), "Essa compra já foi concluída");

        this.transacoes.add(transacao);

    }

    private Set<Transacao> transacoesConcluidasComSucesso() {
        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes
                .stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet());
        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,
                "Existem mais de uma transação concluída para essa compra");
        return transacoesConcluidasComSucesso;
    }

    public Boolean processadaComSucesso() {
        return !transacoesConcluidasComSucesso().isEmpty();
    }

}
