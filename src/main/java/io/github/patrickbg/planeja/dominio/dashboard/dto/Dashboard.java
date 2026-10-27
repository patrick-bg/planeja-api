package io.github.patrickbg.planeja.dominio.dashboard.dto;

import java.util.List;

public record Dashboard(
        String mes,
        ResumoFinanceiro resumoFinanceiro,
        List<DespesaCategoriaResumo> despesasPorCategoria
) {
}
