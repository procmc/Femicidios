package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TipoRelacionController {


    private TipoRelacionService tipoRelacionService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;

    public TipoRelacionController(TipoRelacionService tipoRelacionService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
        super();
        this.tipoRelacionService = tipoRelacionService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
    }
    
    private void validarPerfil() {
    	
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
			
			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

    @GetMapping("/tiporelaciones")
    public String listTipoRelaciones(Model model){
    	
        model.addAttribute("tipoRelaciones", tipoRelacionService.getAllTipoRelaciones());
        return "tipoRelaciones/tipoRelaciones";
    }

    @GetMapping("/tiporelaciones/new")
    public String createTipoRelacionForm(Model model){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				TipoRelacion tipoRelacion = new TipoRelacion();
		        model.addAttribute("tipoRelacion", tipoRelacion);
		        return "tipoRelaciones/create_tipoRelacion";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/tiporelaciones")
    public String saveTipoRelacion(@ModelAttribute("tipoRelacion") TipoRelacion tipoRelacion){
        tipoRelacionService.saveTipoRelacion(tipoRelacion);
        return "redirect:/tiporelaciones";
    }

    @GetMapping("/tiporelaciones/{id}")
    public String deleteTipoRelaciones(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				tipoRelacionService.deleteTipoRelacionById(id);
		        return "redirect:/tiporelaciones";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/tiporelaciones/edit/{id}")
    public String editTipoRelacionForm(@PathVariable Integer id, Model model){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				 model.addAttribute("tipoRelacion", tipoRelacionService.getTipoRelacionById(id));
			     return "tipoRelaciones/edit_tipoRelacion";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
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
