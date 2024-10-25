package br.edu.univille.poo.pneuxpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.poo.pneuxpress.entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long>{
    
}
