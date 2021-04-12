package br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.pagamento;

import br.com.zup.eduardoribeiro.mercadolivre.treinomercadolivre.compra.Compra;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Compra compra;

    @NotBlank
    @Column(nullable = false)
    private String transacaoId;

    @NotNull
    @Column(nullable = false)
    private Boolean valida;

    @NotNull
    @PastOrPresent
    @CreationTimestamp
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @Deprecated
    public Transacao() {
    }

    public Transacao(@NotNull Compra compra,
                     @NotBlank String transacaoId,
                     @NotNull Boolean valida) {
        this.compra = compra;
        this.transacaoId = transacaoId;
        this.valida = valida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return transacaoId.equals(transacao.transacaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transacaoId);
    }

    public Boolean concluidaComSucesso() {
        return valida;
    }
}
