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

import br.edu.univille.poo.pneuxpress.entity.Produto;
import br.edu.univille.poo.pneuxpress.service.CaracteristicasService;
import br.edu.univille.poo.pneuxpress.service.MarcaService;
import br.edu.univille.poo.pneuxpress.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CaracteristicasService caracteristicasService;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("produto/index");
        mv.addObject("lista", service.obterTodos());
        mv.addObject("listaMarca", marcaService.obterTodos());
        mv.addObject("listaCaracteristicas", caracteristicasService.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("produto/novo");
        mv.addObject("elemento", new Produto());
        mv.addObject("listaMarca", marcaService.obterTodos());
        mv.addObject("listaCaracteristicas", caracteristicasService.obterTodos());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@Valid @ModelAttribute("elemento") Produto produto, BindingResult bindingResult){
        try{
            if ( bindingResult.hasErrors() ) {
                var mv = new ModelAndView("produto/novo");
                mv.addObject("elemento", produto);
                mv.addObject("listaMarca", marcaService.obterTodos());
                mv.addObject("listaCaracteristicas", caracteristicasService.obterTodos());
                return mv;
            }

            // produto.getCaracteristicas()
            service.salvar(produto);
            return new ModelAndView("redirect:/produto");
        }catch (Exception e){
            var mv = new ModelAndView("produto/novo");
            mv.addObject("elemento", produto);
            mv.addObject("listaMarca", marcaService.obterTodos());
            mv.addObject("listaCaracteristicas", caracteristicasService.obterTodos());
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("produto/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            mv.addObject("listaMarca", marcaService.obterTodos());
            mv.addObject("listaCaracteristicas", caracteristicasService.obterTodos());
            return mv;
        }

        return new ModelAndView("redirect:/produto");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("produto/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/produto");
    }
}
