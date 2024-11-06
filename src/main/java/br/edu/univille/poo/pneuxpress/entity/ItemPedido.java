package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    private int quantidade;
    private float imposto;
    private float custo;

    public double calculaCusto() {
        custo = (getProduto().getPreco() * quantidade) + imposto;
        return custo;
    }
}
