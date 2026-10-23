package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.common.validation.CampoInvalido;
import io.github.patrickbg.planeja.common.validation.ValidationResult;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import io.github.patrickbg.planeja.dominio.categoria.model.CategoriaEntity;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoForm;
import io.github.patrickbg.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.stereotype.Component;

@Component
public class LancamentoValidator {

    public ValidationResult validar (LancamentoForm form, CategoriaEntity categoria, CartaoEntity cartao){
        var result = ValidationResult.novo();

        if(categoria == null){
            result.add(new CampoInvalido("categoriaId", "Categoria não encontrada."));
        } else if(Boolean.FALSE.equals(categoria.getAtivo())){
            result.add(new CampoInvalido("categoriaId", "Categoria inativa."));
        }

        if(form.tipo() == TipoLancamento.RECEITA && cartao != null){
            result.add(new CampoInvalido("cartaoId", "Lançamento do tipo RECEITA não aceita cartão."));
        }

        if(form.tipo() == TipoLancamento.DESPESA && form.cartaoId() != null){
            if(cartao == null){
                result.add(new CampoInvalido("cartaoId", "Cartão não encontrado."));
            } else if (Boolean.FALSE.equals(cartao.getAtivo())) {
                result.add(new CampoInvalido("cartaoId", "Cartão inativo."));
            }
        }

        return result;
    }

}
