package com.if7100.controller;

import com.if7100.entity.TipoVictima;
import com.if7100.service.TipoVictimaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TipoVictimaController {

    private TipoVictimaService tipoVictimaService;

    public TipoVictimaController(TipoVictimaService tipoVictimaService) {
        super();
        this.tipoVictimaService = tipoVictimaService;
    }

    @GetMapping("/tipovictimas")
    public String listTipoVictimas(Model model){
        model.addAttribute("tipoVictimas", tipoVictimaService.getAllTipoVictimas());
        return "tipoVictimas";
    }

    @GetMapping("/tipovictimas/new")
    public String createTipoVictimaForm(Model model){
        TipoVictima tipoVictima = new TipoVictima();
        model.addAttribute("tipoVictima", tipoVictima);
        return "create_tipoVictima";
    }

    @PostMapping("/tipovictimas")
    public String saveTipoVictima(@ModelAttribute("tipoVictima") TipoVictima tipoVictima){
        tipoVictimaService.saveTipoVictima(tipoVictima);
        return "redirect:/tipovictimas";
    }

    @GetMapping("/tipovictimas/{id}")
    public String deleteTipoVictimas(@PathVariable Integer id){
        tipoVictimaService.deleteTipoVictimaById(id);
        return "redirect:/tipovictimas";
    }

    @GetMapping("/tipovictimas/edit/{id}")
    public String editTipoVictimaForm(@PathVariable Integer id, Model model){
        model.addAttribute("tipoVictima", tipoVictimaService.getTipoVictimaById(id));
        return "edit_tipoVictima";
    }

    @PostMapping("/tipovictimas/{id}")
    public String updateTipoVictima(@PathVariable Integer id, @ModelAttribute("tipoVictima") TipoVictima tipoVictima){
        TipoVictima existingTipoVictima = tipoVictimaService.getTipoVictimaById(id);
        existingTipoVictima.setCI_Codigo(id);
        existingTipoVictima.setCVTitulo(tipoVictima.getCVTitulo());
        existingTipoVictima.setCVDescripcion(tipoVictima.getCVDescripcion());
        tipoVictimaService.updateTipoVictima(existingTipoVictima);
        return "redirect:/tipovictimas";
    }

}
