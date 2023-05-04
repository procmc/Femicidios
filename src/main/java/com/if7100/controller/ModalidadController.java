package com.if7100.controller;

import com.if7100.entity.Modalidad;
import com.if7100.service.ModalidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ModalidadController {

    private ModalidadService modalidadService;

    public ModalidadController(ModalidadService modalidadService) {
        super();
        this.modalidadService = modalidadService;
    }

    @GetMapping("/modalidades")
    public String listModalidades(Model model){
        model.addAttribute("modalidades", modalidadService.getAllModalidades());
        return "modalidades";
    }

    @GetMapping("/modalidades/new")
    public String createModalidadForm(Model model){
        Modalidad modalidad = new Modalidad();
        model.addAttribute("modalidad", modalidad);
        return "create_modalidad";
    }

    @PostMapping("/modalidades")
    public String saveModalidad(@ModelAttribute("modalidad")Modalidad modalidad){
        modalidadService.saveModalidad(modalidad);
        return "redirect:/modalidades";
    }

    @GetMapping("/modalidades/{id}")
    public String deleteModalidad(@PathVariable Integer id){
        modalidadService.deleteModalidadById(id);
        return "redirect:/modalidades";
    }

    @GetMapping("/modalidades/edit/{id}")
    public String editModalidadForm(@PathVariable Integer id, Model model){
        model.addAttribute("modalidad", modalidadService.getModalidadById(id));
        return "edit_modalidad";
    }

    @PostMapping("/modalidades/{id}")
    public String updateModalidad(@PathVariable Integer id, @ModelAttribute("modalidad") Modalidad modalidad){
        Modalidad existingModalidad = modalidadService.getModalidadById(id);
        existingModalidad.setCI_Codigo(id);
        existingModalidad.setCVTitulo(modalidad.getCVTitulo());
        existingModalidad.setCVDescripcion(modalidad.getCVDescripcion());
        modalidadService.updateModalidad(existingModalidad);
        return "redirect:/modalidades";
    }

}
