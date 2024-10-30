package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.univille.poo.pneuxpress.service.ModeloService;

@Controller
@RequestMapping("/modelo")
public class ModeloController {
    
    @Autowired
    private ModeloService service;
}
