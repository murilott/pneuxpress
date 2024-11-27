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

import br.edu.univille.poo.pneuxpress.entity.ItemPedido;
import br.edu.univille.poo.pneuxpress.entity.Sessao;
import br.edu.univille.poo.pneuxpress.entity.Usuario;
import br.edu.univille.poo.pneuxpress.service.SessaoService;
import br.edu.univille.poo.pneuxpress.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/sessao")
public class SessaoController {
    
    @Autowired
    private SessaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("sessao/index");
        mv.addObject("lista", service.obterTodos());
        
        return mv;
    }

    @PostMapping
    @RequestMapping("/login")
    public ModelAndView login(@Valid Usuario usuario, BindingResult bindingResult){
        // var mv = new ModelAndView("usuario/index");

        Usuario usuarioLogar = usuarioService.obterPeloEmail(usuario.getEmail());
        
        if (usuarioLogar.getSenha().equals(usuario.getSenha())) {
            Sessao ses = service.obterSessaoInfo();

            // if (service.obterTodos().size() == 0) {
            //     ses = new Sessao();
            // } else {
            //     ses = service.obterTodos().get(0);
            // }

            ses.setUsuarioAtual(usuarioLogar);
            ses = service.salvar(ses);
            // mv.addObject("sessao", ses);
        }
        
        // mv.addObject("listaSessao", service.obterTodos());
        // mv.addObject("lista", usuarioService.obterTodos());
        // return mv;
        return new ModelAndView("redirect:/usuario");
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("sessao/novo");
        mv.addObject("sessao", new Sessao());
        return mv;
    }

    @PostMapping
    @RequestMapping("/salvar")
    public ModelAndView salvarNovo(@ModelAttribute("elemento") Sessao sessao){
        try{
            service.salvar(sessao);
            return new ModelAndView("redirect:/sessao");
        }catch (Exception e){
            var mv = new ModelAndView("sessao/novo");
            mv.addObject("elemento", sessao);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("sessao/editar");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            return mv;
        }

        return new ModelAndView("redirect:/sessao");
    }

    @GetMapping
    @RequestMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable long id){
        var mv = new ModelAndView("sessao/editar");
        var opt = service.obterPeloId(id);

        if(opt.isPresent()) {
            service.excluir(opt.get());
        }

        return new ModelAndView("redirect:/sessao");
    }
}
