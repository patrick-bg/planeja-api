package io.github.patrickbg.planeja.dominio.cartao;

import io.github.patrickbg.planeja.common.exceptions.ValidationException;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoForm;
import io.github.patrickbg.planeja.dominio.cartao.mapper.CartaoMapper;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoValidator cartaoValidator;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoMapper cartaoMapper;

    public CartaoDetalhes criar(CartaoForm form){
        var result = cartaoValidator.validar(form);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        CartaoEntity entity = cartaoMapper.toEntity(form);
        cartaoRepository.save(entity);
        return cartaoMapper.toDetalhes(entity);
    }
}
