package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.univille.poo.pneuxpress.entity.Sessao;
import br.edu.univille.poo.pneuxpress.service.SessaoService;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private SessaoService sessaoService;

    @ModelAttribute("sessao")
        public Sessao obterSessao() {
        return sessaoService.obterSessaoInfo();
    }
}