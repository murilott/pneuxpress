package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomeCompleto;
    private String email;
    private Date dataNascimento;
    private String senha;
    private boolean admin;
    private List<Pedido> pedidos;

}
