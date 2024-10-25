package br.edu.univille.poo.pneuxpress.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.univille.poo.pneuxpress.repository.ProdutoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;
}
