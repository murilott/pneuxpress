package br.edu.univille.poo.pneuxpress.controller;

import java.util.ArrayList;
import java.util.List;

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
        
        ItemPedido novoItem = new ItemPedido();
        // Pedido novoPedido = pedidoService.salvar(new Pedido());
        // novoItem.setPedido(novoPedido);
        // novoItem = service.salvar(novoItem);

        // mv.addObject("novoPedido", new Pedido());
        // mv.addObject("novoItem", new ItemPedido());
        
        mv.addObject("listaProduto", produtoService.obterTodos());
        mv.addObject("listaPedido", pedidoService.obterTodos());
        mv.addObject("lista", service.obterTodos());
        mv.addObject("novoItem", novoItem);

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

    @PostMapping
    @RequestMapping("/incrementar")
    public ModelAndView incluirItemPedido(ItemPedido item) { //, ItemPedido item
        try{
            var mv = new ModelAndView("itemPedido/index");
            item.setCusto(item.calculaCusto());
            
            if (item.getPedido() == null || item.getPedido().getId() == 0) {
                Pedido novoPedido = new Pedido();
                novoPedido = pedidoService.salvar(novoPedido); 
                
                item.setPedido(novoPedido); 
                // mv.addObject("print", "Entrou no if");
            } 
            // mv.addObject("print", "Passou fora if");

            Pedido pedido = pedidoService.obterPeloId(item.getPedido().getId()).get();
            pedido.getItens().add(item);

            mv.addObject("print", pedido.getItens());
            
            // item.setPedido(null);
            
            ItemPedido novoItem = new ItemPedido();
            novoItem.setPedido(pedido);
            
            item = service.salvar(item);
            // mv.addObject("print", novoItem.getPedido().getId());

            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("listaPedido", pedidoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            // mv.addObject("novoItem", new ItemPedido());

            mv.addObject("novoItem", novoItem);
            mv.addObject("pedido", item.getPedido());
            // mv.addObject("novoPedido", new Pedido());

            return mv;
        } catch (Exception e){
            var mv = new ModelAndView("itemPedido/index");
            // mv.addObject("novoItem", item);
            // mv.addObject("novoPedido", new Pedido());
            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            mv.addObject("novoItem", new ItemPedido());
            mv.addObject("print", item.getPedido());
            mv.addObject("erro", e.getMessage());
            // mv.addObject("erro", e.getMessage());

            return mv;
        }
    }
   

    // https://github.com/murilott/sisacademia/blob/main/src/main/java/br/univille/sisacademia/controller/RotinaController.java
    // god https://medium.com/@AlexanderObregon/data-mapping-with-springs-modelattribute-annotation-b41704c2521a

    
    @RequestMapping("/salvarAntigo")
    public ModelAndView incluirItemPedidoAntigo(@ModelAttribute("item") ItemPedido item) { //, Pedido pedido
        try{
            var mv = new ModelAndView("itemPedido/index");
            item.setCusto(item.calculaCusto());

            service.salvar(item);

            ItemPedido novoItem = new ItemPedido();

            var opt = pedidoService.obterPeloId(item.getPedido().getId());

            if (opt.isPresent()) {
                Pedido pedido = opt.get();
                pedido.getItens().add(item);
                pedidoService.salvar(pedido);

                item.setPedido(pedido);
                novoItem.setPedido(pedido);
                mv.addObject("pedido", pedido);
            } else {
                Pedido pedido = new Pedido();
                pedido.getItens().add(item);
                pedidoService.salvar(pedido);

                item.setPedido(pedido);
                novoItem.setPedido(pedido);
                mv.addObject("pedido", pedido);
            }

            service.salvar(item);
            
            
            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("listaPedido", pedidoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            mv.addObject("item", novoItem);
            
            return mv;
        } catch (Exception e){
            var mv = new ModelAndView("itemPedido/index");
            // mv.addObject("item", item);
            mv.addObject("listaProduto", produtoService.obterTodos());
            mv.addObject("listaPedido", pedidoService.obterTodos());
            mv.addObject("lista", service.obterTodos());
            // mv.addObject("pedido", pedido);
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
