package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.poo.pneuxpress.entity.Marca;
import br.edu.univille.poo.pneuxpress.entity.Pedido;
import br.edu.univille.poo.pneuxpress.service.MarcaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/marca")
public class MarcaController {
    
    @Autowired
    private MarcaService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("marca/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("marca/novo");
        mv.addObject("elemento", new Marca());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") Marca marca, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("marca/novo");
                mv.addObject("elemento", marca);
                return mv;
            }
            
            service.salvar(marca);
            return new ModelAndView("redirect:/marca");
        }catch (Exception e){
            var mv = new ModelAndView("marca/novo");
            mv.addObject("elemento", marca);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("marca/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/marca");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("marca/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/marca");
    }
}
