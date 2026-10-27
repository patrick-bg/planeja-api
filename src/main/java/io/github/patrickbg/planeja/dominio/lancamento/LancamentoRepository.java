package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.dominio.lancamento.model.LancamentoEntity;
import io.github.patrickbg.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LancamentoRepository extends JpaRepository<LancamentoEntity, UUID>,
        JpaSpecificationExecutor<LancamentoEntity> {

    @Query("""
        select  coalesce (sum (l.valor), 0)
            from LancamentoEntity as l
            where l.tipo = :tipo
            and l.data between :inicio and :fim
""")
    BigDecimal somaPorTipoNoPeriodo (
            @Param("tipo") TipoLancamento tipo,
            @Param("inicio") LocalDate dataInicio,
            @Param("fim") LocalDate dataFim
    );

    @Query("""
        select l.categoria.nome, coalesce (sum(l.valor), 0)
            from LancamentoEntity as l
            where l.tipo = :tipo
            and l.data between :inicio and :fim
            group by l.categoria.nome
            order by sum(l.valor) desc
""")
    List<Object[]> somaPorCategoriaNoPeriodo(
            @Param("tipo") TipoLancamento tipo,
            @Param("inicio") LocalDate dataInicio,
            @Param("fim") LocalDate dataFim
    );

    
}
