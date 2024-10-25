package br.edu.univille.poo.pneuxpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.poo.pneuxpress.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
}
