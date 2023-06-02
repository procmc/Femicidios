/**
 * 
 */
package com.if7100.controller;
import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;

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
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


	public RegistroPerfilController(BitacoraService bitacoraService,
PerfilService perfilService, UsuarioRepository usuarioRepository) {
		super();
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService= bitacoraService;

	}
	
	 private void validarPerfil() {
	    	
			try {
				 Usuario usuario=new Usuario();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			    String username = authentication.getName();
			   
			    this.usuario= new Usuario(usuarioRepository.findByCVCedula(username));

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
				
				String descripcion = "Elimino un perfil";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				
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
