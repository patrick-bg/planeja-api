package io.github.patrickbg.planeja.infra;

import io.github.patrickbg.planeja.dominio.cartao.CartaoRepository;
import io.github.patrickbg.planeja.dominio.cartao.model.BandeiraCartao;
import io.github.patrickbg.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Sandbox implements CommandLineRunner {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void salvarCartao(){
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNome("ITAU");
        cartao.setBandeira(BandeiraCartao.ELO);

        cartaoRepository.save(cartao);
    }

    @Override
    public void run(String... args) throws Exception {
        salvarCartao();
    }
}
