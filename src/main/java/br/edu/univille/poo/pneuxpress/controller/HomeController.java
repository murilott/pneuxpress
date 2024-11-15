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
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("home/index");
        return mv;
    }
}
