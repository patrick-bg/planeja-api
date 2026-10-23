package io.github.patrickbg.planeja.dominio.lancamento.mapper;

import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import io.github.patrickbg.planeja.dominio.categoria.model.CategoriaEntity;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoForm;
import io.github.patrickbg.planeja.dominio.lancamento.model.LancamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {
    LancamentoEntity toEntity(LancamentoForm form, CategoriaEntity categoria, CartaoEntity cartao);

    LancamentoDetalhes toDetalhes(LancamentoEntity entity);
}
