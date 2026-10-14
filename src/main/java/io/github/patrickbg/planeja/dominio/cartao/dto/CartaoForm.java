package io.github.patrickbg.planeja.dominio.cartao.dto;

import io.github.patrickbg.planeja.dominio.cartao.model.BandeiraCartao;

public record CartaoForm(String nome, BandeiraCartao bandeira) {
}
