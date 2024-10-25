package br.edu.univille.poo.pneuxpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.poo.pneuxpress.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
