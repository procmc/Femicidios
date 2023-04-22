package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.service.HechoService;
import com.if7100.service.ModalidadService;
import com.if7100.service.RelacionService;
import com.if7100.service.TipoVictimaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HechoController {

    private HechoService hechoService;
    private ModalidadService modalidadService;
    private TipoVictimaService tipoVictimaService;
    private RelacionService tipoRelacionService;

//    public HechoController(HechoService hechoService) {
//        super();
//        this.hechoService = hechoService;
//    }

    public HechoController(HechoService hechoService, ModalidadService modalidadService, TipoVictimaService tipoVictimaService, RelacionService tipoRelacionService) {
        super();
        this.hechoService = hechoService;
        this.modalidadService = modalidadService;
        this.tipoVictimaService = tipoVictimaService;
        this.tipoRelacionService = tipoRelacionService;
    }

    @GetMapping("/hechos")
    public String listHechos(Model model){
        model.addAttribute("hechos", hechoService.getAllHechos());
        return "hechos";
    }

    @GetMapping("/hechos/new")
    public String createHechoForm(Model model){
        Hecho hecho = new Hecho();
        model.addAttribute("hecho", hecho);
        model.addAttribute("modalidad", modalidadService.getAllModalidad());
        model.addAttribute("tipoVictima", tipoVictimaService.getAllTipoVictima());
        model.addAttribute("tipoRelacion", tipoRelacionService.getAllTypeRelaciones());
        return "create_hecho";
    }

//    @PostMapping("/guardar")
//    public String guardarHecho(@ModelAttribute("hecho") Hecho hecho, BindingResult result, Model model) {
//        try {
//            hechoRepository.save(hecho);
//            return "redirect:/hechos";
//        } catch (DataIntegrityViolationException e) {
//            // Manejar la excepción aquí
//            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
//            model.addAttribute("error", mensaje);
//            return "formularioHecho";
//        }
//    }

    @PostMapping("/hechos")
    public String saveHecho(@ModelAttribute("hecho") Hecho hecho, Model model){
        try {
            hechoService.saveHecho(hecho);
            return "redirect:/hechos";
        } catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoForm(model);
        }
    }

    @GetMapping("/hechos/{id}")
    public String deleteHecho(@PathVariable Integer id){
        hechoService.deleteHechoById(id);
        return "redirect:/hechos";
    }

    @GetMapping("/hechos/edit/{id}")
    public String editHechoForm(@PathVariable Integer id, Model model){
        model.addAttribute("hecho", hechoService.getHechoById(id));
        model.addAttribute("modalidad", modalidadService.getAllModalidad());
        model.addAttribute("tipoVictima", tipoVictimaService.getAllTipoVictima());
        model.addAttribute("tipoRelacion", tipoRelacionService.getAllTypeRelaciones());
        return "edit_hecho";
    }

    @PostMapping("/hechos/{id}")
    public String updateHecho(@PathVariable Integer id, @ModelAttribute("hecho") Hecho hecho, Model model){
        try {
            Hecho existingHecho = hechoService.getHechoById(id);
            existingHecho.setCI_Id(id);
            existingHecho.setCVLugar(hecho.getCVLugar());
            existingHecho.setCVTipoVictima(hecho.getCVTipoVictima());
            existingHecho.setCVTipoRelacion(hecho.getCVTipoRelacion());
            existingHecho.setCVModalidad(hecho.getCVModalidad());
            hechoService.updateHecho(existingHecho);
            return "redirect:/hechos";
        } catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return editHechoForm(id, model);
        }
    }
}
