package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.poo.pneuxpress.entity.Modelo;
import br.edu.univille.poo.pneuxpress.repository.ModeloRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository repository;

    public Optional<Modelo> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Modelo> obterTodos(){
        return repository.findAll(Sort.by("nome"));
    }

    public void salvar(Modelo modelo) {
        repository.save(modelo);
    }

    public void excluir(Modelo modelo) {
        repository.delete(modelo);
    }
}
