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
 * @author Hadji
 *
 */


import com.if7100.service.VictimaService;
import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.entity.Victima;
import com.if7100.repository.UsuarioRepository;

@Controller
public class VictimaController {
	
	private VictimaService victimaService;
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
	
	public VictimaController (VictimaService victimaService, PerfilService perfilService, UsuarioRepository usuarioRepository)
	{
		super();
		this.victimaService=victimaService;
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

	@GetMapping("/victima")
	public String listStudents(Model model) {
		model.addAttribute("victima", victimaService.getAllVictima());
		return "victimas/victima";
	}
	
	@GetMapping("/victima/new")
	public String createVictimaForm (Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				Victima victima = new Victima();
				model.addAttribute("victima", victima);
				return "victimas/create_victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	
	@PostMapping("/victima")
	public String saveVictima (@ModelAttribute("victima") Victima victima) {
		
		victimaService.saveVictima(victima);
		return "redirect:/victima";
	}
	
	@GetMapping("/victima/{Id}")
	public String deleteVictima (@PathVariable Integer Id) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				victimaService.deleteVictimaById(Id);
				return "redirect:/victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	
	@GetMapping("/victima/edit/{id}")
	public String editVictimaForm (@PathVariable Integer id, Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("victima", victimaService.getVictimaById(id));
				return "victimas/edit_victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}	
	}
	
	
	@PostMapping("/victima/{id}")
	public String updateVictima (@PathVariable Integer id, 
								 @ModelAttribute("victima") Victima victima,
								 Model model) {
		
		Victima existingVictima = victimaService.getVictimaById(id);
		existingVictima.setCI_Id(id);
		existingVictima.setCVDNI(victima.getCVDNI());
		existingVictima.setCVNombre(victima.getCVNombre());
		existingVictima.setCVApellidoPaterno(victima.getCVApellidoPaterno());
		existingVictima.setCVApellidoMaterno(victima.getCVApellidoMaterno());
		existingVictima.setCVEdad(victima.getCVEdad());
		existingVictima.setCVGenero(victima.getCVGenero());
		existingVictima.setCVLugarNac(victima.getCVLugarNac());
		existingVictima.setCVOrientaSex(victima.getCVOrientaSex());
		
		victimaService.updateVictima(existingVictima);
		return "redirect:/victima";
		
	}
	
}
