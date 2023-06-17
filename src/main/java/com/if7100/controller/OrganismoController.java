package com.if7100.controller;

import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

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
import com.if7100.entity.Organismo;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.OrganismoService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoOrganismoService;


@Controller
public class OrganismoController {
	
 private OrganismoService organismoService;
 private TipoOrganismoService tipoOrganismoService;

//instancias para control de acceso
 private UsuarioRepository usuarioRepository;
 private Perfil perfil;
 private PerfilService perfilService;
//instancias para control de bitacora
private BitacoraService bitacoraService;
private Usuario usuario;

 
 
 public OrganismoController (BitacoraService bitacoraService,
OrganismoService organismoService, TipoOrganismoService tipoOrganismoService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	 super();
	 this.organismoService=organismoService;
	 this.perfilService = perfilService;
     this.usuarioRepository = usuarioRepository;
     this.tipoOrganismoService=tipoOrganismoService;
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
 
 @GetMapping("/organismos")
 public String listOrganismos(Model model) {
	 
	 return "redirect:/organismo/1";
 }

 @GetMapping("/organismo/{pg}")
 public String listOrganismo(Model model, @PathVariable Integer pg){

	 if (pg < 1){
		 return "redirect:/organismo/1";
	 }

	 int numeroTotalElementos = organismoService.getAllOrganismos().size();

	 Pageable pageable = initPages(pg, 5, numeroTotalElementos);

	 Page<Organismo> organismoPage = organismoService.getAllOrganismosPage(pageable);

	 List<Integer> nPaginas = IntStream.rangeClosed(1, organismoPage.getTotalPages())
			 .boxed()
			 .toList();

	 model.addAttribute("PaginaActual", pg);
	 model.addAttribute("nPaginas", nPaginas);
	 model.addAttribute("organismos", organismoPage.getContent());
	 return "organismos/organismos";
 }
 
 @GetMapping("/organismos/new")
 public String createOrganismoForm(Model model) {
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("organismo",new Organismo());
				model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
				return "organismos/create_organismo";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @PostMapping("/organismos")
 public String savOrganismo(@ModelAttribute("organismo") Organismo organismo) {
	 
	 if (!organismo.getCVNombre().equals("") && !organismo.getCVRol().equals("") && 
	 !organismo.getCVNacionalidad().equals("") && !organismo.getCVContacto().equals("") &&
	 !organismo.getCVTipo_Organismo().equals("")){
		 organismoService.saveOrganismo(organismo);
		 return "redirect:/organismos";
	}
	 
	 return "redirect:/organismos";
	 
 }
 
 @GetMapping("/organismos/{id}")
 public String deleteOrganismo(@PathVariable int id) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				String descripcion = "Elimino un organismo";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				
				organismoService.deleteOrganismoById(id);
				 return "redirect:/organismos";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @GetMapping("/organismos/edit/{id}")
 public String editOrganismoForm(Model model,@PathVariable int id) {
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("organismo", organismoService.getOrganismoById(id));
				model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
				return "organismos/edit_organismo";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @PostMapping("/organismos/{id}")
 public String updateOrganismo(@PathVariable int id, @ModelAttribute("organismo") Organismo organismo, Model model) {
	 Organismo existingOrganismo=organismoService.getOrganismoById(id);
	 model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());

	 existingOrganismo.setCI_Id(id);
	 existingOrganismo.setCVNombre(organismo.getCVNombre());
	 existingOrganismo.setCVRol(organismo.getCVRol());
	 existingOrganismo.setCVTipo_Organismo(organismo.getCVTipo_Organismo());
	 existingOrganismo.setCVNacionalidad(organismo.getCVNacionalidad());
	 existingOrganismo.setCVContacto(organismo.getCVContacto());

	 
	 organismoService.updateOrganismo(existingOrganismo);
	
	 return "redirect:/organismos";
 }
 
 
 
 
}
