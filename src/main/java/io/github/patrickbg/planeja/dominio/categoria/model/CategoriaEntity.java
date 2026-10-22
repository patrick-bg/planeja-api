package io.github.patrickbg.planeja.dominio.categoria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "categoria")
@Getter
@Setter
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "nome", nullable = false, length = 30)
    private String nome;
    @Column
    private Boolean ativo;

    @PrePersist
    public void prePersist(){
        setAtivo(true);
    }
}
