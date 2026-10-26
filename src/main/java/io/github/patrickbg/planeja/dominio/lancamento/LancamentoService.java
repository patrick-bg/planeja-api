package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.common.exceptions.ValidationException;
import io.github.patrickbg.planeja.dominio.cartao.CartaoRepository;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import io.github.patrickbg.planeja.dominio.categoria.CategoriaRepository;
import io.github.patrickbg.planeja.dominio.categoria.model.CategoriaEntity;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoForm;
import io.github.patrickbg.planeja.dominio.lancamento.mapper.LancamentoMapper;
import io.github.patrickbg.planeja.dominio.lancamento.model.LancamentoEntity;
import io.github.patrickbg.planeja.dominio.lancamento.model.TipoLancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.UUID;

import static io.github.patrickbg.planeja.dominio.lancamento.LancamentoSpecs.*;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LancamentoValidator lancamentoValidator;

    @Autowired
    private LancamentoMapper lancamentoMapper;

    public LancamentoDetalhes criar (LancamentoForm form){
        CategoriaEntity categoria = categoriaRepository.findById(form.categoriaId())
                .orElse(null);

        CartaoEntity cartao = null;
        if(form.cartaoId() != null){
            cartao = cartaoRepository.findById(form.cartaoId()).orElse(null);
        }

        var result = lancamentoValidator.validar(form, categoria, cartao);
        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        LancamentoEntity entity = lancamentoMapper.toEntity(form,categoria,cartao);
        lancamentoRepository.save(entity);

        return lancamentoMapper.toDetalhes(entity);
    }

    public Page<LancamentoDetalhes> listar (
            PageRequest pageRequest, YearMonth mes, TipoLancamento tipo, UUID categoriaId){
        Specification<LancamentoEntity> spec = Specification.unrestricted();

        if(tipo != null){
            spec = spec.and(tipoEqual(tipo));
        }

        if(categoriaId != null){
            spec = spec.and(categoriaEqual(categoriaId));
        }

        if(mes != null){
            spec = spec.and(mesEqual(mes));
        }

        var resultado = lancamentoRepository.findAll(spec, pageRequest);
        return resultado.map(lancamentoMapper::toDetalhes);
    }
}
