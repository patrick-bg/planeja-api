package io.github.patrickbg.planeja.dominio.categoria;

import io.github.patrickbg.planeja.dominio.categoria.dto.CategoriaDetalhes;
import io.github.patrickbg.planeja.dominio.categoria.dto.CategoriaForm;

public interface CategoriaMapper {
    CategoriaEntity toEntity(CategoriaForm nova);

    CategoriaDetalhes toDetalhes(CategoriaEntity entity);
}