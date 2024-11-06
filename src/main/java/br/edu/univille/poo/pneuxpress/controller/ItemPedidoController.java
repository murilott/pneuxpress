package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.univille.poo.pneuxpress.entity.ItemPedido;
import br.edu.univille.poo.pneuxpress.service.ItemPedidoService;

@Controller
@RequestMapping("/itempedido")
public class ItemPedidoController {
    
    @Autowired
    private ItemPedidoService service;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("itemPedido/index");
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("itemPedido/novo");
        mv.addObject("elemento", new ItemPedido());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@ModelAttribute("elemento") ItemPedido itemPedido){
        try{
            service.salvar(itemPedido);
            return new ModelAndView("redirect:/itemPedido");
        }catch (Exception e){
            var mv = new ModelAndView("itemPedido/novo");
            mv.addObject("elemento", itemPedido);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("itemPedido/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/itemPedido");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("itemPedido/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/itemPedido");
    }
}
