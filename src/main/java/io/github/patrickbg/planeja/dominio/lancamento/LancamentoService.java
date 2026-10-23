package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.dominio.cartao.CartaoRepository;
import io.github.patrickbg.planeja.dominio.categoria.CategoriaRepository;
import io.github.patrickbg.planeja.dominio.lancamento.mapper.LancamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
