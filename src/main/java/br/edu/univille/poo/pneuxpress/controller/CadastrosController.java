package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.poo.pneuxpress.service.ProdutoService;
import br.edu.univille.poo.pneuxpress.service.MarcaService;

@Controller
@RequestMapping("/cadastros")
public class CadastrosController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("cadastros/index");
        // mv.addObject("listaProduto", marcaService.obterTodos());
        return mv;
    }
}
