package io.github.patrickbg.planeja.dominio.dashboard;

import io.github.patrickbg.planeja.dominio.dashboard.dto.Dashboard;
import io.github.patrickbg.planeja.dominio.dashboard.dto.DespesaCategoriaResumo;
import io.github.patrickbg.planeja.dominio.dashboard.dto.ResumoFinanceiro;
import io.github.patrickbg.planeja.dominio.lancamento.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Dashboard obterDashboardMesAtual(){
        YearMonth mesAtual = YearMonth.now();

        BigDecimal receitas = null;
        BigDecimal despesas = null;
        BigDecimal saldo = receitas.subtract(despesas);

        ResumoFinanceiro resumoFinanceiro = new ResumoFinanceiro(receitas, despesas, saldo);

        List<DespesaCategoriaResumo> despesasPorCategoria = null;

        return new Dashboard(mesAtual.toString(), resumoFinanceiro, despesasPorCategoria);
    }

}
