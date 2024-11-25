package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity

public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.MERGE) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @NotNull(message = "Campo produto não pode ser em branco")
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @NotNull(message = "Campo quantidade não pode ser em branco")
    @Min(value = 1, message = "Campo quantidade deve conter, pelo menos, 1 item")
    private int quantidade;
    @NotNull(message = "Campo imposto não pode ser em branco")
    private float imposto;
    private double custo;

    public double calculaCusto() {
        custo = (getProduto().getPreco() * quantidade) + imposto;
        return custo;
    }

    @Override
    public String toString() {
        return "ItemPedido{id=" + id + ", pedidoId=" + pedido.getId() + ", produto=" + produto.getNomeDisplay() + ", quantidade=" + quantidade + ", custo=" + custo + "}";
    }
}
