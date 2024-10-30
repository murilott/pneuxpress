package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.poo.pneuxpress.entity.Marca;
import br.edu.univille.poo.pneuxpress.repository.MarcaRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository repository;

    public Optional<Marca> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Marca> obterTodos(){
        return repository.findAll(Sort.by("nome"));
    }

    public void salvar(Marca marca) {
        if(Strings.isBlank(marca.getNome())){
            throw new RuntimeException("Nome não informado.");
        }
        repository.save(marca);
    }

    public void excluir(Marca marca) {
        repository.delete(marca);
    }
}
