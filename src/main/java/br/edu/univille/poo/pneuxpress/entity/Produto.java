package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomeDisplay;
    @OneToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;
    @OneToOne
    @JoinColumn(name = "caracteristicas_id")
    private Caracteristicas caracteristicas;
    @OneToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;
    private float preco;
    private int quantidadeEstoque;
    private String imagem;
}
