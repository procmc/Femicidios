/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.entity.Paises;
import com.if7100.service.*;
import com.if7100.service.PaisesService;
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

import com.if7100.service.PerfilService;
/**
 * @author Adam Smasher
 */
import com.if7100.service.TipoOrganismoService;
import com.if7100.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class TipoOrganismoController {
private TipoOrganismoService tipoOrganismoService;
private PaisesService paisesService;
//instancias para control de acceso
private UsuarioRepository usuarioRepository;
private Perfil perfil;
private Paises paises;
private PerfilService perfilService;
//instancias para control de bitacora
private BitacoraService bitacoraService;
private Usuario usuario;

	public TipoOrganismoController(BitacoraService bitacoraService,
TipoOrganismoService tipoOrganismoService, PaisesService paisesService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	super();
	this.tipoOrganismoService= tipoOrganismoService;
	this.perfilService = perfilService;
    this.usuarioRepository = usuarioRepository;
    this.bitacoraService= bitacoraService;
    this.paisesService = paisesService;

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

	@GetMapping("/tipoOrganismo")
	public String listStudents(Model model) {
		return "redirect:/tipoorganismo/1";
	}

	@GetMapping("/tipoorganismo/{pg}")
	public  String listOrganismo(Model model, @PathVariable Integer pg){
		if (pg < 1){
			return "redirect:/tipoorganismo/1";
		}

		int numeroTotalElementos = tipoOrganismoService.getAllTipoOrganismos().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		Page<TipoOrganismo> tipoOrganismoPage = tipoOrganismoService.getAllTipoOrganismosPage(pageable);

		List<Integer> nPaginas = IntStream.rangeClosed(1, tipoOrganismoPage.getTotalPages())
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("tipoOrganismos", tipoOrganismoPage.getContent());
		return "tipoOrganismo/tipoOrganismo";
	}

	@GetMapping("/tipoOrganismo/new")
	public String CreateTipoOrganismoForm (Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				
                model.addAttribute("tipoOrganismo", new TipoOrganismo());
				model.addAttribute("paises", paisesService.getAllPaises());
				return "tipoOrganismo/create_tipoOrganismo";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/tipoOrganismo")
	public String saveTipoOrganismo (@ModelAttribute TipoOrganismo tipoOrganismo) {
	    tipoOrganismoService.saveTipoOrganismo(tipoOrganismo);
		return "redirect:/tipoOrganismo";	
	}
	

	@GetMapping("/tipoOrganismo/{Codigo}")
	public String deleteTipoOrganismoByCodigo (@PathVariable Integer Codigo) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				String descripcion = "Elimino un tipo de organismo";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				
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
				model.addAttribute("paises", paisesService.getAllPaises());
				return "tipoOrganismo/edit_tipoOrganismo";	
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}	
	}
	
	@PostMapping("/tipoOrganismo/{Codigo}")
	public String updateOrganismoLugar (@PathVariable Integer Codigo ,@ModelAttribute TipoOrganismo tipoOrganismo, Model model) {
	   
		TipoOrganismo existingTipoOrganismo = tipoOrganismoService.getTipoOrganismoByCodigo(Codigo);
		model.addAttribute("paises", paisesService.getAllPaises());
		
		existingTipoOrganismo.setCI_Codigo(Codigo);
	    existingTipoOrganismo.setCVTitulo(tipoOrganismo.getCVTitulo());
	    existingTipoOrganismo.setCVDescripcion(tipoOrganismo.getCVDescripcion());
	    existingTipoOrganismo.setCVPaises(tipoOrganismo.getCVPaises());
		tipoOrganismoService.updateTipoOrganismo(existingTipoOrganismo);
		return "redirect:/tipoOrganismo";	
	}
}
