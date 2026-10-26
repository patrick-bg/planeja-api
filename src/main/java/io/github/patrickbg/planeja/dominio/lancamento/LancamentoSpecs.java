package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.dominio.lancamento.model.LancamentoEntity;
import io.github.patrickbg.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public class LancamentoSpecs {

    private LancamentoSpecs(){}

    public static Specification<LancamentoEntity> tipoEqual (TipoLancamento tipo){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("tipo"), tipo);
        };
    }

    public static Specification<LancamentoEntity> categoriaEqual (UUID categoriaId){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("categoria").get("id"), categoriaId);
        };
    }

    public static Specification<LancamentoEntity> mesEqual (YearMonth mes){
        return (root, query, criteriaBuilder) -> {
            LocalDate inicioMes = mes.atDay(1);
            LocalDate fimMes = mes.atEndOfMonth();

            return criteriaBuilder.between(root.get("data"), inicioMes, fimMes);
        };
    }
}
