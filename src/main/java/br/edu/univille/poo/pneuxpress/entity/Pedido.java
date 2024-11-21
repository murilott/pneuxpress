package br.edu.univille.poo.pneuxpress.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // @OneToMany
    // @JoinColumn(name = "pedido_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<ItemPedido>();
    private float custoTotal;

    public float calculaCustoTotal() {
        for (int i=0; i<itens.size(); i++) {
            custoTotal += itens.get(i).getCusto();
        }
        
        return custoTotal;
    }
}
