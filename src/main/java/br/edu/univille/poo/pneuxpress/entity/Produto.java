package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Campo nome não pode ser em branco")
    @Size(min = 5, max = 100, message = "Campo nome deve ter entre 5 e 100 caracteres")
    private String nomeDisplay;
    @NotNull(message = "Campo marca não pode ser em branco")
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_produto_marca"))
    private Marca marca;
    @NotNull(message = "Campo caracteristicas não pode ser em branco")
    @ManyToOne
    @JoinColumn(name = "caracteristicas_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_produto_caracteristicas"))
    private Caracteristicas caracteristicas;
    @NotNull(message = "Campo preço não pode ser em branco")
    @Min(value = 1, message = "Campo preço deve ser 1 R$ ou acima")
    private float preco;
    @NotNull(message = "Campo quantidade não pode ser em branco")
    @Min(value = 1, message = "Campo quantidade deve conter, pelo menos, 1 item")
    private int quantidadeEstoque;
    private String imagem;
}
