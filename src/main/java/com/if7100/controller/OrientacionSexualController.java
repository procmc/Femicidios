package com.if7100.controller;

import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;

import com.if7100.entity.Hecho;
import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.OrientacionSexualService;
import com.if7100.service.PerfilService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.OrientacionSexual;
import com.if7100.service.OrientacionSexualService;

@Controller
public class OrientacionSexualController {
	
 private OrientacionSexualService orientacionService;
//instancias para control de acceso
 private UsuarioRepository usuarioRepository;
 private Perfil perfil;
 private PerfilService perfilService;
//instancias para control de bitacora
private BitacoraService bitacoraService;
private Usuario usuario;

 
 public OrientacionSexualController(BitacoraService bitacoraService,
OrientacionSexualService orientacionService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	 super();
	 this.orientacionService=orientacionService;
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
 
 @GetMapping("/orientacionessexuales")
 public String listOrientacionesSexuales(Model model) {
	 
	 model.addAttribute("orientacionesSexuales",orientacionService.getAllOrientacionesSexuales());
	 return "orientacionesSexuales/orientacionesSexuales";
 }
 
 @GetMapping("/orientacionessexuales/new")
 public String createOrientacionSexualForm(Model model) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("orientacionSexual",new OrientacionSexual());
				return "orientacionesSexuales/create_orientacionSexual";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }

 @GetMapping("/orientacionesSexuales")
 public String listStudents(Model model) {
	 
	 model.addAttribute("orientacionesSexuales",orientacionService.getAllOrientacionesSexuales());
	 return "orientacionessexuales/orientacionesSexuales";
 }
 
 @GetMapping("/orientacionesSexuales/new")
 public String createUsuarioForm(Model model) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("orientacion",new OrientacionSexual());
				return "orientacionessexuales/create_orientacionesSexuales";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @PostMapping("/orientacionesSexuales")
 public String saveOrientacion(@ModelAttribute("orientacion") OrientacionSexual orientacion) {
	 orientacionService.saveOrientacionSexual(orientacion);
	 return "redirect:/orientacionesSexuales";
 }
 
 @GetMapping("/orientacionesSexuales/{id}")
 public String deleteOrientacion(@PathVariable int id) {

	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				
				orientacionService.deleteOrientacionSexualByCodigo(id);
				 return "redirect:/orientacionesSexuales";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @GetMapping("/orientacionesSexuales/edit/{id}")
 public String editOrientacionForm(Model model,@PathVariable int id) {
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("orientacion", orientacionService.getOrientacionSexualByCodigo(id));
				 return "orientacionessexuales/edit_orientacionesSexuales";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @PostMapping("/orientacionesSexuales/{id}")
 public String updateOrientacionSexual(@PathVariable int id, @ModelAttribute("orientacion") OrientacionSexual orientacion, Model model) {
	 OrientacionSexual existingOrientacion=orientacionService.getOrientacionSexualByCodigo(id);
	 existingOrientacion.setCI_Codigo(id);
	 existingOrientacion.setCVTitulo(orientacion.getCVTitulo());
	 existingOrientacion.setCVDescripcion(orientacion.getCVDescripcion());
	 
	 orientacionService.updateOrientacionSexual(existingOrientacion);
	 return "redirect:/orientacionesSexuales";
 }
 
 
 
 
}
