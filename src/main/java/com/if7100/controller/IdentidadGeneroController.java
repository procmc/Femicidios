/**
 * 
 */
package com.if7100.controller;




import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.Paises;
import com.if7100.service.BitacoraService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.entity.Hecho;
import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Perfil;
import com.if7100.service.IdentidadGeneroService;


import com.if7100.service.PerfilService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author michael arauz torrez
 *
 */
@Controller
public class IdentidadGeneroController {

	/**
	 * 
	 */
	private IdentidadGeneroService identidadGeneroService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;
	


	public IdentidadGeneroController(BitacoraService bitacoraService, IdentidadGeneroService identidadGeneroService,
			PerfilService perfilService, UsuarioRepository usuarioRepository
			) {
		super();
		this.identidadGeneroService = identidadGeneroService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		
	
	}

	private void validarPerfil() {

		try {
			Usuario usuario = new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));

			this.perfil = new Perfil(
					perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

		} catch (Exception e) {
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
	
	@GetMapping("/identidadgenero")
	public String listStudents(Model model) {
//		model.addAttribute("identidadgenero", identidadGeneroService.getAllIdentidadGenero());
//		return "identidadGeneros/identidadgenero";
		return "redirect:/identidadesgenero/1";
	}

	@GetMapping("identidadesgenero/{pg}")
	public String listIdentidadesGeneros(Model model, @PathVariable Integer pg){

		if (pg < 1){
			return "redirect:/identidadesgenero/1";
		}

		int numeroTotalElementos = identidadGeneroService.getAllIdentidadGenero().size();
		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		Page<IdentidadGenero> identidadGeneroPage = identidadGeneroService.getAllIdentidadGeneroPage(pageable);

		List<Integer> nPaginas = IntStream.rangeClosed(1, identidadGeneroPage.getTotalPages())
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("identidadesGenero", identidadGeneroPage.getContent());
		return "identidadGeneros/identidadgenero";
	}

	@GetMapping("/identidadgenero/new")
	public String createIdentidadGenero(Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				IdentidadGenero identidadgenero = new IdentidadGenero();
				Paises paises = new Paises();
				model.addAttribute("identidadgenero", identidadgenero);
				
				return "identidadGeneros/crear_identidad";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/identidadgenero")
	public String saveIdentidadGenero(@ModelAttribute("identidadgenero") IdentidadGenero identidadgenero) {
		identidadGeneroService.saveIdentidadGenero(identidadgenero);
		String descripcion = "Guado una identidad de genero";
		Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol() ,
				descripcion);
		bitacoraService.saveBitacora(bitacora);
		return "redirect:/identidadgenero";
	}
	@GetMapping("/identidadgenero/edit/{id}")
	public String editIdentidadGenero(@PathVariable Integer id, Model model) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("identidadgenero", identidadGeneroService.getIdentidadGeneroById(id));
				
				return "identidadGeneros/edit_identidadgenero";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/identidadgenero/{id}")
	public String updateUsuario(@PathVariable Integer id,
			@ModelAttribute("identidadgenero") IdentidadGenero identidadgenero, Model model) {
		IdentidadGenero existingIdentidadGenero = identidadGeneroService.getIdentidadGeneroById(id);
		existingIdentidadGenero.setId(id);
		existingIdentidadGenero.setNombre(identidadgenero.getNombre());
		existingIdentidadGenero.setDescripcion(identidadgenero.getDescripcion());
		identidadGeneroService.updateIdentidadGenero(identidadgenero);
		String descripcion = "Actualizo una identidad de genero";
		Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol() ,
				descripcion);
		bitacoraService.saveBitacora(bitacora);
		return "redirect:/identidadgenero";
	}
	
	@GetMapping("/identidadgenero/{Id}")
	public String deleteidentidadgenero(@PathVariable Integer Id) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				String descripcion = "Elimino una identidad de genero";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol() ,
						descripcion);
				bitacoraService.saveBitacora(bitacora);

				identidadGeneroService.deleteIdentidadGeneroById(Id);
				return "redirect:/identidadgenero";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}


}