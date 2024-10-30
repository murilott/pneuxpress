package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.poo.pneuxpress.entity.Caracteristicas;
import br.edu.univille.poo.pneuxpress.repository.CaracteristicasRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class CaracteristicasService {
    @Autowired
    private CaracteristicasRepository repository;

    public Optional<Caracteristicas> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Caracteristicas> obterTodos(){
        return repository.findAll(Sort.by("nome"));
    }

    public void salvar(Caracteristicas caracteristicas) {
        if(Strings.isBlank(caracteristicas.getNome())){
            throw new RuntimeException("Nome não informado.");
        }
        repository.save(caracteristicas);
    }

    public void excluir(Caracteristicas caracteristicas) {
        repository.delete(caracteristicas);
    }
}
