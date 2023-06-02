package com.if7100.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Perfil;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;
import com.if7100.service.ProcesoJudicialService;

@Controller
public class ProcesoJudicialController {

 private ProcesoJudicialService procesoJudicialService;
//instancias para control de acceso
 private UsuarioRepository usuarioRepository;
 private Perfil perfil;
 private PerfilService perfilService;
//instancias para control de bitacora
private BitacoraService bitacoraService;
private Usuario usuario;


 public ProcesoJudicialController (BitacoraService bitacoraService,ProcesoJudicialService procesoJudicialService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	 super();
	 this.procesoJudicialService=procesoJudicialService;
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

 @GetMapping("/procesosJudiciales")
 public String listStudents(Model model) {

	 model.addAttribute("procesosJudiciales",procesoJudicialService.getAllProcesosJudiciales());
	 return "procesosJudiciales/procesosJudiciales";
 }

 @GetMapping("/procesosJudiciales/new")
 public String createProcesoJudicialForm(Model model) {

	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("procesoJudicial",new ProcesoJudicial());
				return "procesosJudiciales/create_procesosJudiciales";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }

 @PostMapping("/procesosJudiciales")
 public String saveProcesoJudicial(@ModelAttribute("procesoJudicial") ProcesoJudicial procesoJudicial) {
	 procesoJudicialService.saveProcesoJudicial(procesoJudicial);
	 return "redirect:/procesosJudiciales";
 }

 @GetMapping("/procesosJudiciales/{id}")
 public String deleteProcesoJudicial(@PathVariable int id) {

	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				//Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
				//bitacoraService.saveBitacora(bitacora);

				procesoJudicialService.deleteProcesoJudicialById(id);
				return "redirect:/procesosJudiciales";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }

 @GetMapping("/procesosJudiciales/edit/{id}")
 public String editProcesoJudicialForm(Model model,@PathVariable int id) {

	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("procesoJudicial", procesoJudicialService.getProcesoJudicialById(id));
				 return "procesosJudiciales/edit_procesosJudiciales";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }

 @PostMapping("/procesosJudiciales/{id}")
 public String updateProcesoJudicial(@PathVariable int id, @ModelAttribute("procesoJudicial") ProcesoJudicial procesoJudicial, Model model) {
	 ProcesoJudicial existingProcesoJudicial=procesoJudicialService.getProcesoJudicialById(id);
	 existingProcesoJudicial.setCI_Id(id);
	 existingProcesoJudicial.setCVEstado(procesoJudicial.getCVEstado());
	 existingProcesoJudicial.setCIDenunciante(procesoJudicial.getCIDenunciante());
	 //existingProcesoJudicial.setCDFechaApertura(procesoJudicial.getCDFechaApertura());
	 existingProcesoJudicial.setCIPersonasImputadas(procesoJudicial.getCIPersonasImputadas());
	 existingProcesoJudicial.setCVPartes(procesoJudicial.getCVPartes());

	 procesoJudicialService.updateProcesoJudicial(existingProcesoJudicial);
	 return "redirect:/procesosJudiciales";
 }




}
