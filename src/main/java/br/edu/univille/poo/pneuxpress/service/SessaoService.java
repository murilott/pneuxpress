package br.edu.univille.poo.pneuxpress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.univille.poo.pneuxpress.entity.Sessao;
import br.edu.univille.poo.pneuxpress.entity.Usuario;
import br.edu.univille.poo.pneuxpress.repository.SessaoRepository;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository repository;

    public Optional<Sessao> obterPeloId(long id){
        return repository.findById(id);
    }
    
    public Sessao obterSessaoInfo() {
        if (obterTodos().size() == 0) {
            return new Sessao();
        } else {
            return obterTodos().get(0);
        }
    }

    public List<Sessao> obterTodos(){
        return repository.findAll();
    }

    public Sessao salvar(Sessao sessao) {
        return repository.save(sessao);
    }

    public void excluir(Sessao sessao) {
        repository.delete(sessao);
    }
}
