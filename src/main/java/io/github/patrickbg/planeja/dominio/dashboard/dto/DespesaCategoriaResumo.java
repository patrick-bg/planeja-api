package io.github.patrickbg.planeja.dominio.dashboard.dto;

import java.math.BigDecimal;

public record DespesaCategoriaResumo(
        String categoria,
        BigDecimal valor
) {
}
