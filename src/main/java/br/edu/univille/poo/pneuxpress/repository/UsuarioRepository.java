package br.edu.univille.poo.pneuxpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.poo.pneuxpress.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
