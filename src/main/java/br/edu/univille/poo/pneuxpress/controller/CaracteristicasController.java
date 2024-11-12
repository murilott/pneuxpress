package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.poo.pneuxpress.entity.Caracteristicas;
import br.edu.univille.poo.pneuxpress.service.CaracteristicasService;

@Controller
@RequestMapping("/caracteristicas")
public class CaracteristicasController {
    
    @Autowired
    private CaracteristicasService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("caracteristicas/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("caracteristicas/novo");
        mv.addObject("elemento", new Caracteristicas());
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@ModelAttribute("elemento") Caracteristicas caracteristicas){
        try{
            service.salvar(caracteristicas);
            return new ModelAndView("redirect:/caracteristicas");
        }catch (Exception e){
            var mv = new ModelAndView("caracteristicas/novo");
            mv.addObject("elemento", caracteristicas);
            mv.addObject("lista", service.obterTodos());
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("caracteristicas/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            mv.addObject("lista", service.obterTodos());
            return mv;
        }

        return new ModelAndView("redirect:/caracteristicas");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("caracteristicas/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/caracteristicas");
    }
    
}
