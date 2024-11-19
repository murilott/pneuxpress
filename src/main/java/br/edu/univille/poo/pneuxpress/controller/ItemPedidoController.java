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
import br.edu.univille.poo.pneuxpress.entity.Pedido;
import br.edu.univille.poo.pneuxpress.service.ItemPedidoService;
import br.edu.univille.poo.pneuxpress.service.PedidoService;
import br.edu.univille.poo.pneuxpress.service.ProdutoService;

@Controller
@RequestMapping("/itemPedido")
public class ItemPedidoController {
    
    @Autowired
    private ItemPedidoService service;
    
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("itemPedido/index");
        mv.addObject("elemento", new ItemPedido());
        mv.addObject("pedido", new Pedido());
        mv.addObject("listaProduto", produtoService.obterTodos());
        mv.addObject("listaPedido", pedidoService.obterTodos());
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    @GetMapping
    @RequestMapping("/novo")
    public ModelAndView novo(){
        var mv = new ModelAndView("itemPedido/novo");
        mv.addObject("elemento", new ItemPedido());
        mv.addObject("listaProduto", produtoService.obterTodos());
        mv.addObject("lista", service.obterTodos());
        return mv;
    }

    // https://github.com/murilott/sisacademia/blob/main/src/main/java/br/univille/sisacademia/controller/RotinaController.java

    @RequestMapping("/salvar")
    public ModelAndView incluirItemPedido(@ModelAttribute("elemento") ItemPedido item, Pedido pedido) {
        try{
            var mv = new ModelAndView("itemPedido/index");
            // ItemPedido item = itemPedido;
            item.setCusto(item.calculaCusto());
            // Pedido ped = pedido;
            pedido.getItens().add(item);
            // pedido.getItens().add(item);

            // pedidoService.salvar(pedido);
            service.salvar(item);

            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("listaPedido", pedidoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            mv.addObject("elemento", new ItemPedido());
            mv.addObject("pedido", pedido);
            return mv;
        } catch (Exception e){
            var mv = new ModelAndView("itemPedido/index");
            mv.addObject("elemento", item);
            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("listaPedido", pedidoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            mv.addObject("pedido", pedido);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @PostMapping
    @RequestMapping("/finalizar")
    public ModelAndView finalizar(@ModelAttribute("elemento") ItemPedido itemPedido){
        try{
            ItemPedido item = itemPedido;
            item.setCusto(item.calculaCusto());

            // nss q perda de tempo - inútil
            Pedido pedido = pedidoService.obterPedidoPeloItem(item);

            pedido.getItens().add(item);
            pedidoService.salvar(pedido);

            service.salvar(item);
            return new ModelAndView("redirect:/itemPedido");
        }catch (Exception e){
            var mv = new ModelAndView("itemPedido/novo");
            mv.addObject("elemento", itemPedido);
            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ModelAndView editar(@PathVariable long id){
        var mv = new ModelAndView("itemPedido/index");
        var opt = service.obterPeloId(id);
        
        if(opt.isPresent()) {
            mv.addObject("elemento", opt.get());
            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
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
