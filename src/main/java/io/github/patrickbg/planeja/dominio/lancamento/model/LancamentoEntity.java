package io.github.patrickbg.planeja.dominio.lancamento.model;

import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import io.github.patrickbg.planeja.dominio.categoria.model.CategoriaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
public class LancamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @JoinColumn(name = "categoria_id", nullable = false)
    @ManyToOne
    private CategoriaEntity categoria;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private CartaoEntity cartao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;
}
