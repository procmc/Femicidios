package com.if7100.controller;

import com.if7100.entity.TipoRelacion;
import com.if7100.service.TipoRelacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TipoRelacionController {


    private TipoRelacionService tipoRelacionService;

    public TipoRelacionController(TipoRelacionService tipoRelacionService) {
        super();
        this.tipoRelacionService = tipoRelacionService;
    }

    @GetMapping("/tiporelaciones")
    public String listTipoRelaciones(Model model){
        model.addAttribute("tipoRelaciones", tipoRelacionService.getAllTipoRelaciones());
        return "tipoRelaciones";
    }

    @GetMapping("/tiporelaciones/new")
    public String createTipoRelacionForm(Model model){
        TipoRelacion tipoRelacion = new TipoRelacion();
        model.addAttribute("tipoRelacion", tipoRelacion);
        return "create_tipoRelacion";
    }

    @PostMapping("/tiporelaciones")
    public String saveTipoRelacion(@ModelAttribute("tipoRelacion") TipoRelacion tipoRelacion){
        tipoRelacionService.saveTipoRelacion(tipoRelacion);
        return "redirect:/tiporelaciones";
    }

    @GetMapping("/tiporelaciones/{id}")
    public String deleteTipoRelaciones(@PathVariable Integer id){
        tipoRelacionService.deleteTipoRelacionById(id);
        return "redirect:/tiporelaciones";
    }

    @GetMapping("/tiporelaciones/edit/{id}")
    public String editTipoRelacionForm(@PathVariable Integer id, Model model){
        model.addAttribute("tipoRelacion", tipoRelacionService.getTipoRelacionById(id));
        return "edit_tipoRelacion";
    }

    @PostMapping("/tiporelaciones/{id}")
    public String updateTipoRelacion(@PathVariable Integer id, @ModelAttribute("tipoRelacion") TipoRelacion tipoRelacion){
        TipoRelacion existingTipoRelacion = tipoRelacionService.getTipoRelacionById(id);
        existingTipoRelacion.setCI_Codigo(id);
        existingTipoRelacion.setCVTitulo(tipoRelacion.getCVTitulo());
        existingTipoRelacion.setCVDescripcion(tipoRelacion.getCVDescripcion());
        tipoRelacionService.updateTipoRelacion(existingTipoRelacion);
        return "redirect:/tiporelaciones";
    }

}
