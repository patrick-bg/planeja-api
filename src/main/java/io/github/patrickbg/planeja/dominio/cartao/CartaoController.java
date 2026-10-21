package io.github.patrickbg.planeja.dominio.cartao;

import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.patrickbg.planeja.dominio.cartao.dto.CartaoForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("cartoes")
@CrossOrigin("*")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoDetalhes> criar(@RequestBody @Valid CartaoForm cartaoForm) {
        CartaoDetalhes detalhes = cartaoService.criar(cartaoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
    }

    @GetMapping("{id}")
    public ResponseEntity<CartaoDetalhes> obterDetalhes (@PathVariable UUID id) {
        var result = cartaoService.obterDetalhes(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar (@PathVariable UUID id, @RequestBody @Valid CartaoForm dadosAtualizacao) {
        cartaoService.atualizar(id, dadosAtualizacao);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<CartaoDetalhes> listar (@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size){
        var pageRequest = PageRequest.of(page, size);
        return cartaoService.listar(pageRequest);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<Void> mudarStatus (@PathVariable UUID id){
        cartaoService.mudarStatus(id);
        return ResponseEntity.noContent().build();
    }

}
