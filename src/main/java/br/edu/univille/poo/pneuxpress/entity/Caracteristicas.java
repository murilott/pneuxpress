package br.edu.univille.poo.pneuxpress.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class Caracteristicas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Campo categoria não pode ser em branco")
    @Size(min = 1, max = 30, message = "Campo categoria deve ter entre 1 e 30 caracteres")
    private String categoria;
    @NotBlank(message = "Campo largura não pode ser em branco")
    @Size(min = 1, max = 30, message = "Campo largura deve ter entre 1 e 30 caracteres")
    private String largura;
    @NotBlank(message = "Campo perfil não pode ser em branco")
    @Size(min = 1, max = 30, message = "Campo perfil deve ter entre 1 e 30 caracteres")
    private String perfil;
    @NotBlank(message = "Campo aro não pode ser em branco")
    @Size(min = 1, max = 30, message = "Campo aro deve ter entre 1 e 30 caracteres")
    private String aro;

    public String nome() {
        return categoria + " " + largura + " " + perfil + " " + aro;
    }
}
