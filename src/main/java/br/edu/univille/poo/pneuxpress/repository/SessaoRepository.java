package br.edu.univille.poo.pneuxpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.poo.pneuxpress.entity.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Long>{
    
}
