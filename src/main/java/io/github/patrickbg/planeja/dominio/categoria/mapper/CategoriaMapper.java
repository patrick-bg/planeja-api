package io.github.patrickbg.planeja.dominio.categoria.mapper;

import io.github.patrickbg.planeja.dominio.categoria.dto.CategoriaDetalhes;
import io.github.patrickbg.planeja.dominio.categoria.dto.CategoriaForm;
import io.github.patrickbg.planeja.dominio.categoria.model.CategoriaEntity;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaEntity toEntity(CategoriaForm nova);

    CategoriaDetalhes toDetalhes(CategoriaEntity entity);
}