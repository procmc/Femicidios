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

import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;


@Controller
public class RegistroPerfilController {

	private PerfilService perfilService;
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;

	public RegistroPerfilController(PerfilService perfilService, UsuarioRepository usuarioRepository) {
		super();
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
	
	@GetMapping("/perfiles")
	public String listPerfiles(Model model) {
		
		try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {
				
				model.addAttribute("perfiles", perfilService.getAllPerfiles());
				return "perfiles/perfiles";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@GetMapping("/registroPerfil")
	public String createPerfilForm (Model model) {
		
		try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {
				
				Perfil perfil = new Perfil();
				model.addAttribute("perfil", perfil);
				return "perfiles/registroPerfil";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/registroPerfil")
	public String savePerfil (@ModelAttribute("perfil") Perfil perfil) {
		
		perfilService.savePerfil(perfil);
		return "redirect:/perfiles";
	}
	
	@GetMapping("/perfiles/{id}")
	public String deletePerfil (@PathVariable Integer id) {
		
		try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {
				
				perfilService.deletePerfilById(id);
				return "redirect:/perfiles";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@GetMapping("/perfiles/edit/{id}")
	public String editUsuarioForm (@PathVariable Integer id, Model model) {
		
		try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {
				
				model.addAttribute("perfil", perfilService.getPerfilById(id));
				return "perfiles/edit_perfil";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/perfiles/{id}")
	public String updateUsuario (@PathVariable Integer id, @ModelAttribute("perfil") Perfil perfil) {
		
		Perfil existingUsuario = perfilService.getPerfilById(id);
		existingUsuario.setCI_Id(id);
		existingUsuario.setCVDescripcion(perfil.getCVDescripcion());
		existingUsuario.setCVRol(perfil.getCVRol());
		
		perfilService.updatePerfil(existingUsuario);
		return "redirect:/perfiles";
	}
}
