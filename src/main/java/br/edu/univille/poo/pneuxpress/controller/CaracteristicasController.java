package br.edu.univille.poo.pneuxpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.univille.poo.pneuxpress.service.CaracteristicasService;

@Controller
@RequestMapping("/caracteristicas")
public class CaracteristicasController {
    
    @Autowired
    private CaracteristicasService service;
}
