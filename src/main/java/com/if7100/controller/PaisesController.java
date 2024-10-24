package com.if7100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.if7100.entity.Paises;
import com.if7100.service.PaisesService;

@Controller
public class PaisesController {

    @Autowired
    private PaisesService paisesService;

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        // Obtener la lista de países desde el servicio
        List<Paises> codigosPaises = paisesService.getAllPaises();
        model.addAttribute("codigosPaises", codigosPaises);
        return "formulario"; 
    }
}
