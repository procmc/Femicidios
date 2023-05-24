/**
 * 
 */
package com.if7100.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.service.PerfilService;
/**
 * @author Adam Smasher
 */
import com.if7100.service.TipoOrganismoService;
import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoOrganismo;
import com.if7100.repository.UsuarioRepository;

@Controller
public class TipoOrganismoController {
private TipoOrganismoService tipoOrganismoService;
//instancias para control de acceso
private UsuarioRepository usuarioRepository;
private Perfil perfil;
private PerfilService perfilService;
	
	public TipoOrganismoController(TipoOrganismoService tipoOrganismoService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	super();
	this.tipoOrganismoService= tipoOrganismoService;
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

	@GetMapping("/tipoOrganismo")
	public String listStudents(Model model) {
		model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
		return "tipoOrganismo/tipoOrganismo";
	}

	@GetMapping("/tipoOrganismo/new")
	public String CreateTipoOrganismoForm (Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				TipoOrganismo tipoOrganismo= new TipoOrganismo();
				model.addAttribute("tipoOrganismo", tipoOrganismo);
				return "tipoOrganismo/create_tipoOrganismo";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/tipoOrganismo")
	public String saveTipoOrganismo (@ModelAttribute("tipoOrganismo") TipoOrganismo tipoOrganismo) {
	    tipoOrganismoService.saveTipoOrganismo(tipoOrganismo);
		return "redirect:/tipoOrganismo";	
	}
	

	@GetMapping("/tipoOrganismo/{Codigo}")
	public String deleteTipoOrganismoByCodigo (@PathVariable Integer Codigo) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				tipoOrganismoService.deleteTipoOrganismoByCodigo(Codigo);
				return "redirect:/tipoOrganismo";	
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	

	@GetMapping("/tipoOrganismo/edit/{Codigo}")
	public String editTipoOrganismoForm (@PathVariable Integer Codigo,Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("tipoOrganismo", tipoOrganismoService.getTipoOrganismoByCodigo(Codigo));
				return "tipoOrganismo/edit_tipoOrganismo";	
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}	
	}
	
	@PostMapping("/tipoOrganismo/{Codigo}")
	public String updateOrganismoLugar (@PathVariable Integer Codigo ,@ModelAttribute("tipoOrganismo") TipoOrganismo tipoOrganismo, Model model) {
	   
		TipoOrganismo existingTipoOrganismo = tipoOrganismoService.getTipoOrganismoByCodigo(Codigo);
	    existingTipoOrganismo.setCI_Codigo(Codigo);
	    existingTipoOrganismo.setCVTitulo(tipoOrganismo.getCVTitulo());
	    existingTipoOrganismo.setCVDescripcion(tipoOrganismo.getCVDescripcion());
		tipoOrganismoService.updateTipoOrganismo(existingTipoOrganismo);
		return "redirect:/tipoOrganismo";	
	}
}
