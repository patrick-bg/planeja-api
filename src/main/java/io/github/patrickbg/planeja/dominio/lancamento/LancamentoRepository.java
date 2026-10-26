package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.dominio.lancamento.model.LancamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface LancamentoRepository extends JpaRepository<LancamentoEntity, UUID>,
        JpaSpecificationExecutor<LancamentoEntity> {
    
}
