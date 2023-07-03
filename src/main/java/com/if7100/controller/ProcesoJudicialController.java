package com.if7100.controller;

import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.if7100.entity.ProcesoJudicial;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.ProcesoJudicialService;

import java.util.List;
import java.util.stream.IntStream;

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

	private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos){
		int numeroPagina = pg-1;
		if (numeroTotalElementos < 10){
			paginasDeseadas = 1;
		}
		if (numeroTotalElementos < 1){
			numeroTotalElementos = 1;
		}
		int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
		return PageRequest.of(numeroPagina, tamanoPagina);
	}
 
 @GetMapping("/procesosJudiciales")
 public String listStudents(Model model) {
	 
	 return "redirect:/procesojudicial/1";
 }

 @GetMapping("/procesojudicial/{pg}")
 public String listProcesoJudicial(Model model, @PathVariable Integer pg){

	 if (pg < 1){
		 return "redirect:/procesojudicial/1";
	 }

	 int numeroTotalElementos = procesoJudicialService.getAllProcesosJudiciales().size();

	 Pageable pageable = initPages(pg, 5, numeroTotalElementos);

	 Page<ProcesoJudicial> procesoJudicialPage = procesoJudicialService.getAllProcesosJudicialesPage(pageable);

	 List<Integer> nPaginas = IntStream.rangeClosed(1, procesoJudicialPage.getTotalPages())
			 .boxed()
			 .toList();

	 model.addAttribute("PaginaActual", pg);
	 model.addAttribute("nPaginas", nPaginas);
	 model.addAttribute("procesosJudiciales", procesoJudicialPage.getContent());

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
	 
	 String descripcion = "Crea en Proceso Judicial";
	 Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
	 bitacoraService.saveBitacora(bitacora);
	 
	 return "redirect:/procesosJudiciales";
 }
 
 @GetMapping("/procesosJudiciales/{id}")
 public String deleteProcesoJudicial(@PathVariable int id) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				procesoJudicialService.deleteProcesoJudicialById(id);
				String descripcion = "Elimino un proceso judicial";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(),descripcion);
				bitacoraService.saveBitacora(bitacora);
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
	 //existingProcesoJudicial.setCDFechaApertura(procesoJudicial.getCDFechaApertura());
	 existingProcesoJudicial.setCIPersonasImputadas(procesoJudicial.getCIPersonasImputadas());
	 existingProcesoJudicial.setCVAgravantes(procesoJudicial.getCVAgravantes());
	 existingProcesoJudicial.setCVTipoDelito(procesoJudicial.getCVTipoDelito());
	 
	 procesoJudicialService.updateProcesoJudicial(existingProcesoJudicial);
	 
	 String descripcion="Actualiza en Proceso Judicial";
	 Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
	 bitacoraService.saveBitacora(bitacora);
		
	 return "redirect:/procesosJudiciales";
 }
 
 
 
 
}
