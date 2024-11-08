package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.poo.pneuxpress.entity.Produto;
import br.edu.univille.poo.pneuxpress.repository.ProdutoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public Optional<Produto> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Produto> obterTodos(){
        return repository.findAll(Sort.by("marca"));
    }

    public void salvar(Produto produto) {
        repository.save(produto);
    }

    public void excluir(Produto produto) {
        repository.delete(produto);
    }
}
