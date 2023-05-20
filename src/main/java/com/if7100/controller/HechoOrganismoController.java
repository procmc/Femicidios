package com.if7100.controller;

import com.if7100.entity.HechoOrganismo;
import com.if7100.service.HechoService;
import com.if7100.service.OrganismoService;
import com.if7100.service.HechoOrganismoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HechoOrganismoController {

    private HechoOrganismoService hechoOrganismoService;
    private HechoService hechoService;

    private OrganismoService organismoService;

    public HechoOrganismoController(HechoOrganismoService hechoOrganismoService, HechoService hechoService, OrganismoService organismoService, PerfilService perfilService, UsuarioRepository usuarioRepository){
        super();
        this.hechoOrganismoService = hechoOrganismoService;
        this.hechoService = hechoService;
        this.organismoService = organismoService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/hechoorganismo")
    public String listHechoImputado(Model model){
        model.addAttribute("hechoOrganismo", hechoOrganismoService.getAllHechoOrganismo());
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("/hechosorganismo/{id}")
    public String listHechosImputado(Model model, @PathVariable Integer id){
        model.addAttribute("hechoOrganismo", hechoOrganismoService.getAllHechosOrganismo(id));
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("/hechoorganismos/{id}")
    public String listHechoImputados(Model model, @PathVariable Integer id){
        model.addAttribute("hechoOrganismo", hechoOrganismoService.getAllHechoOrganismos(id));
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("hechoorganismo/new")
    public String createHechoOrganismoForm(Model model){
        HechoOrganismo hechoOrganismo = new HechoOrganismo();
        model.addAttribute("hechoOrganismo", hechoOrganismo);
        model.addAttribute("hechos", hechoService.getAllHechos());
        model.addAttribute("organismos", organismoService.getAllOrganismos());
        return "hechosOrganismos/create_hecho_organismo";
    }

    @GetMapping("/hechosorganismo/new/{Id}")
    public String createHechosOrganismoForm(Model model, @PathVariable Integer Id) {
        HechoOrganismo hechoOrganismo = new HechoOrganismo();
        hechoOrganismo.setCIHecho(Id);
        model.addAttribute("hechoOrganismo", hechoOrganismo);
        model.addAttribute("hechos", hechoService.getAllHechos());
        model.addAttribute("organismos", organismoService.getAllOrganismos());
        return "hechosOrganismos/create_hechos_Organismo";
    }

    @GetMapping("/hechoorganismos/new/{Id}")
    public String createHechoOrganismosForm(Model model, @PathVariable Integer Id){
        HechoOrganismo hechoOrganismo = new HechoOrganismo();
        hechoOrganismo.setCIOrganismo(Id);
        model.addAttribute("hechoOrganismo", hechoOrganismo);
        model.addAttribute("hechos", hechoService.getAllHechos());
        model.addAttribute("organismos", organismoService.getAllOrganismos());
        return "hechosOrganismos/create_hecho_organismos";
    }

    @GetMapping("/hechoorganismo/{id}")
    public String deleteHecho(@PathVariable Integer id){
        hechoOrganismoService.deleteHechoOrganismoById(id);
        return "redirect:/hechoorganismo";
    }

    @PostMapping("/hechoorganismo")
    public String saveHechoOrganismo(@ModelAttribute("hechoOrganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            return "redirect:/hechoorganismo";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoOrganismoForm(model);
        }
    }

    @PostMapping("/hechosorganismo")
    public String saveHechosOrganismo(@ModelAttribute("hechoOrganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            return "redirect:/hechoorganismo";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechosOrganismoForm(model, hechoOrganismo.getCIHecho());
        }
    }

    @PostMapping("/hechoorganismos")
    public String saveHechoOrganismos(@ModelAttribute("hechoOrganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            return "redirect:/hechoorganismo";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoOrganismosForm(model, hechoOrganismo.getCIOrganismo());
        }
    }


}

