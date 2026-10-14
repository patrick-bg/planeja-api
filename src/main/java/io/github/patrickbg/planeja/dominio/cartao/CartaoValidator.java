package io.github.patrickbg.planeja.dominio.cartao;

import io.github.patrickbg.planeja.common.validation.CampoInvalido;
import io.github.patrickbg.planeja.common.validation.ValidationResult;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartaoValidator {

    @Autowired
    private CartaoRepository cartaoRepository;


    public ValidationResult validar (CartaoForm form, UUID id){
        var result = ValidationResult.novo();

        var isListNotEmpty = !cartaoRepository.findByNomeAndNotId(form.nome(),id).isEmpty();
        if(isListNotEmpty){
            result.add(new CampoInvalido("nome", "Já cadastrado"));
        }

        return result;
    }
}
