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
import com.if7100.entity.Imputado;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.ImputadoService;
import com.if7100.service.PerfilService;

@Controller
public class ImputadoController {
	
 private ImputadoService imputadoService;
//instancias para control de acceso
 private UsuarioRepository usuarioRepository;
 private Perfil perfil;
 private PerfilService perfilService;
 
 public ImputadoController (ImputadoService imputadoService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	 super();
	 this.imputadoService=imputadoService;
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
 
 @GetMapping("/imputados")
 public String ListImputados(Model model) {
	 
	 model.addAttribute("imputados",imputadoService.getAllUsuarios());
	 return "imputados/imputados";
 }
 
 @GetMapping("/imputados/new")
 public String CreateUsuarioForm(Model model) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("imputado",new Imputado());
				return "imputados/create_imputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @PostMapping("/imputados")
 public String SaveImputado(@ModelAttribute("usuario") Imputado imputado) {
	 imputadoService.saveImputado(imputado);
	 return "redirect:/imputados";
 }
 
 @GetMapping("/imputados/{id}")
 public String deleteUsuario(@PathVariable int id) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				imputadoService.deleteImputadoById(id);
				 return "redirect:/imputados";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @GetMapping("/imputados/edit/{id}")
 public String editImputadosForm(Model model,@PathVariable int id) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("imputado", imputadoService.getImputadoById(id));
				return "imputados/edit_imputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	 
 }
 
 @PostMapping("/imputados/{id}")
 public String updateUsuario(@PathVariable int id, @ModelAttribute("imputado") Imputado imputado, Model model) {
	 Imputado existingImputado=imputadoService.getImputadoById(id);
	 existingImputado.setCVDni(imputado.getCVDni());
	 existingImputado.setCVGenero(imputado.getCVGenero());
	 existingImputado.setCVNombre(imputado.getCVNombre());
	 existingImputado.setCVOrientacionSexual(imputado.getCVOrientacionSexual());
	 existingImputado.setCVLugarNacimiento(imputado.getCVLugarNacimiento());
	 existingImputado.setCIEdad(imputado.getCIEdad());
	 
	 imputadoService.updateImputado(existingImputado);
	 return "redirect:/imputados";
 }
 
 
 
 
}
