package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Campo nome não pode ser em branco")
    private String nomeCompleto;
    @NotBlank(message = "Campo e-mail não pode ser em branco")
    private String email;
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Este campo deve conter uma data no passado")
    @NotNull(message = "Campo Data de Nascimento não pode ser em branco")
    private Date dataNascimento;
    @NotBlank(message = "Campo senha não pode ser em branco")
    private String senha;
    private boolean admin;
    @OneToMany
    @JoinColumn(name = "usuario_id")
    private List<Pedido> pedidos;


    // public String obterItens() {
    //     String itens = "";
    //     for (Pedido pedido : getPedidos()) {
    //         itens = itens + pedido.getItens();
    //     }

    //     return itens;
    // }
}
