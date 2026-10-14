package io.github.patrickbg.planeja.dominio.cartao.mapper;

import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoForm;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartaoMapper {

    CartaoEntity toEntity(CartaoForm form);

    CartaoDetalhes toDetalhes(CartaoEntity entity);
}
