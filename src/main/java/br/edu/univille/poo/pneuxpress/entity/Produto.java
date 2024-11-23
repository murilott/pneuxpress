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
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_produto_marca"))
    private Marca marca;
    @ManyToOne
    @JoinColumn(name = "caracteristicas_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_produto_caracteristicas"))
    private Caracteristicas caracteristicas;
    private float preco;
    private int quantidadeEstoque;
    private String imagem;
}
