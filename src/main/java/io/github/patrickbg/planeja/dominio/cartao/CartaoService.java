package io.github.patrickbg.planeja.dominio.cartao;

import io.github.patrickbg.planeja.common.exceptions.RegistroNaoEncontradoException;
import io.github.patrickbg.planeja.common.exceptions.ValidationException;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoForm;
import io.github.patrickbg.planeja.dominio.cartao.mapper.CartaoMapper;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private CartaoValidator cartaoValidator;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoMapper cartaoMapper;

    public CartaoDetalhes criar(CartaoForm form){
        var result = cartaoValidator.validar(form, null);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        CartaoEntity entity = cartaoMapper.toEntity(form);
        cartaoRepository.save(entity);
        return cartaoMapper.toDetalhes(entity);
    }

    public CartaoDetalhes obterDetalhes(UUID id){
        return cartaoRepository.findById(id)
                .map(cartaoMapper::toDetalhes)
                .orElseThrow(RegistroNaoEncontradoException::new);
    }

    @Transactional
    public void atualizar(UUID id, @Valid CartaoForm dadosAtualizacao) {
        var entity = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        var result = cartaoValidator.validar(dadosAtualizacao, id);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }
        cartaoMapper.update(entity, dadosAtualizacao);
    }

    public Page<CartaoDetalhes> listar(PageRequest pageRequest){
        return cartaoRepository.findAll(pageRequest).map(
                cartaoMapper::toDetalhes);
    }

    @Transactional
    public void mudarStatus(UUID id){
        var cartao = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        cartao.setAtivo(!cartao.getAtivo());
        cartaoRepository.save(cartao);
    }

    public List<CartaoDetalhes> listarAtivos() {
        return cartaoRepository.findByAtivoTrue().stream().map(cartaoMapper::toDetalhes).toList();
    }
}
