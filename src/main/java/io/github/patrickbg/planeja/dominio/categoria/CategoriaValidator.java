package io.github.patrickbg.planeja.dominio.categoria;

import io.github.patrickbg.planeja.common.validation.CampoInvalido;
import io.github.patrickbg.planeja.common.validation.ValidationResult;
import io.github.patrickbg.planeja.dominio.categoria.dto.CategoriaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoriaValidator {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ValidationResult validar(CategoriaForm nova) {
        var result = ValidationResult.novo();
        var existeCategoriaCadastrada = categoriaRepository.existsByNome(nova.nome());
        if(existeCategoriaCadastrada){
            result.add(new CampoInvalido("nome", "Existe categoria cadastrada com este nome."));
        }
        return result;
    }

}
