package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.dominio.cartao.CartaoService;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.patrickbg.planeja.dominio.categoria.CategoriaService;
import io.github.patrickbg.planeja.dominio.categoria.dto.CategoriaDetalhes;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lancamentos")
@CrossOrigin("*")
public class CadastroLancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<LancamentoDetalhes> criar (@RequestBody @Valid LancamentoForm form){
        var detalhes = lancamentoService.criar(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
    }

    @GetMapping("categorias-disponiveis")
    public List<CategoriaDetalhes> listarCategoriasDisponiveis(){
        return categoriaService.listarAtivas();
    }

    @GetMapping("cartoes-disponiveis")
    public List<CartaoDetalhes> listarCartoesDisponiveis(){
        return cartaoService.listarAtivos();
    }


}
