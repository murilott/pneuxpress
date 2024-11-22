package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.poo.pneuxpress.entity.ItemPedido;
import br.edu.univille.poo.pneuxpress.repository.ItemPedidoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class ItemPedidoService {
    @Autowired
    private ItemPedidoRepository repository;

    public Optional<ItemPedido> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<ItemPedido> obterTodos(){
        return repository.findAll(Sort.by("produto"));
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        return repository.save(itemPedido);
    }

    public void excluir(ItemPedido itemPedido) {
        repository.delete(itemPedido);
    }
}
