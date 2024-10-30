package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.poo.pneuxpress.entity.Pedido;
import br.edu.univille.poo.pneuxpress.repository.PedidoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public Optional<Pedido> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Pedido> obterTodos(){
        return repository.findAll(Sort.by("nome"));
    }

    public void salvar(Pedido pedido) {
        if(Strings.isBlank(pedido.getNome())){
            throw new RuntimeException("Nome não informado.");
        }
        repository.save(pedido);
    }

    public void excluir(Pedido pedido) {
        repository.delete(pedido);
    }
}
