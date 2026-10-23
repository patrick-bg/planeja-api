package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.common.validation.ValidationResult;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import io.github.patrickbg.planeja.dominio.categoria.model.CategoriaEntity;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoForm;
import org.springframework.stereotype.Component;

@Component
public class LancamentoValidator {

    public ValidationResult validar (LancamentoForm form, CategoriaEntity categoria, CartaoEntity cartao){
        return ValidationResult.novo();
    }

}
