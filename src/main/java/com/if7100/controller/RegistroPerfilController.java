/**
 * 
 */
package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Perfil;
import com.if7100.service.PerfilService;


@Controller
public class RegistroPerfilController {

	private PerfilService perfilService;

	public RegistroPerfilController(PerfilService perfilService) {
		super();
		this.perfilService = perfilService;
	}
	
	@GetMapping("/perfiles")
	public String listPerfiles(Model model) {
		
		model.addAttribute("perfiles", perfilService.getAllPerfiles());
		return "perfiles";
	}
	
	@GetMapping("/registroPerfil")
	public String createPerfilForm (Model model) {
		
		Perfil perfil = new Perfil();
		model.addAttribute("perfil", perfil);
		return "registroPerfil";
	}
	
	@PostMapping("/registroPerfil")
	public String savePerfil (@ModelAttribute("perfil") Perfil perfil) {
		
		perfilService.savePerfil(perfil);
		return "redirect:/perfiles";
	}
	
	@GetMapping("/perfiles/{id}")
	public String deletePerfil (@PathVariable Integer id) {
		
		perfilService.deletePerfilById(id);
		return "redirect:/perfiles";
	}
	
	@GetMapping("/perfiles/edit/{id}")
	public String editUsuarioForm (@PathVariable Integer id, Model model) {
		
		model.addAttribute("perfil", perfilService.getPerfilById(id));
		return "edit_perfil";
	}
	
	@PostMapping("/perfiles/{id}")
	public String updateUsuario (@PathVariable Integer id, @ModelAttribute("perfil") Perfil perfil) {
		
		Perfil existingUsuario = perfilService.getPerfilById(id);
		existingUsuario.setCI_Id(id);
		existingUsuario.setCVUsername(perfil.getCVUsername());
		existingUsuario.setCVPassword(perfil.getCVPassword());
		existingUsuario.setCVRole(perfil.getCVRole());
		
		perfilService.updatePerfil(existingUsuario);
		return "redirect:/perfiles";
	}
}
