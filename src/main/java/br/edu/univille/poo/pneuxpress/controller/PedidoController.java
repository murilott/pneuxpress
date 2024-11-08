package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.poo.pneuxpress.entity.Pedido;
import br.edu.univille.poo.pneuxpress.service.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("pedido/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("pedido/novo");
        mv.addObject("elemento", new Pedido());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@ModelAttribute("elemento") Pedido pedido){
        try{
            service.salvar(pedido);
            return new ModelAndView("redirect:/pedido");
        }catch (Exception e){
            var mv = new ModelAndView("pedido/novo");
            mv.addObject("elemento", pedido);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("pedido/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/pedido");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("pedido/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/pedido");
    }
}
