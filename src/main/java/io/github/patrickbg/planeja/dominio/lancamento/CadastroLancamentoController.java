package io.github.patrickbg.planeja.dominio.lancamento;

import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import io.github.patrickbg.planeja.dominio.lancamento.dto.LancamentoForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lancamentos")
public class CadastroLancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity<LancamentoDetalhes> criar (@RequestBody @Valid LancamentoForm form){
        var detalhes = lancamentoService.criar(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
    }
}
