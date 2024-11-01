package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.entity.Usuario;
import com.if7100.entity.Modalidad;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;

import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.ModalidadService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;

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

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class ModalidadController {

	private ModalidadService modalidadService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	private PaisesService paisesService;

	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;
	private Paises paises;

	public ModalidadController(ModalidadService modalidadService, PerfilService perfilService,
			UsuarioRepository usuarioRepository,
			BitacoraService bitacoraService, PaisesService paisesService) {
		super();
		this.modalidadService = modalidadService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.paisesService = paisesService;
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

	// @GetMapping("/modalidades")
	// public String listModalidades(Model model){
	// model.addAttribute("modalidades", modalidadService.getAllModalidades());
	// return "modalidades/modalidades";
	// }

	private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos) {
		int numeroPagina = pg - 1;
		if (numeroTotalElementos < 10) {
			paginasDeseadas = 1;
		}
		if (numeroTotalElementos < 1) {
			numeroTotalElementos = 1;
		}
		int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
		return PageRequest.of(numeroPagina, tamanoPagina);
	}

	@GetMapping("/modalidades")
	public String listModalidades(Model model) {
		this.validarPerfil();
		return "redirect:/modalidad/1";
	}

	@GetMapping("/modalidad/{pg}")
	public String listModalidad(Model model, @PathVariable Integer pg) {
		
		this.validarPerfil();

		if (pg < 1) {
			return "redirect:/modalidad/1";
		}

		int numeroTotalElementos = modalidadService.getAllModalidades().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		Page<Modalidad> modalidadPage = modalidadService.getAllModalidadesPage(pageable);

		List<Integer> nPaginas = IntStream.rangeClosed(1, modalidadPage.getTotalPages())
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("modalidades", modalidadPage.getContent());
		return "modalidades/modalidades";
	}

	@GetMapping("/modalidades/new")
	public String createModalidadForm(Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				Modalidad modalidad = new Modalidad();
				model.addAttribute("modalidad", modalidad);
				model.addAttribute("paises", paisesService.getAllPaises());
				return "modalidades/create_modalidad";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}

	}

	@PostMapping("/modalidades")
	public String saveModalidad(@ModelAttribute Modalidad modalidad) {
		// INSERTAR EN BITACORA
		this.validarPerfil();
		String descripcion = "Creo en Modalidad: " + modalidad.getCI_Codigo() + modalidad.getCVTitulo() +
				modalidad.getCVDescripcion();

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en modalidades"));

		modalidadService.saveModalidad(modalidad);
		return "redirect:/modalidades";
	}

	@GetMapping("/modalidades/{id}")
	public String deleteModalidad(@PathVariable Integer id) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				// INSERTAR EN BITACORA
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en modalidades"));
				
				modalidadService.deleteModalidadById(id);
				return "redirect:/modalidades";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/modalidades/edit/{id}")
	public String editModalidadForm(@PathVariable Integer id, Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("modalidad", modalidadService.getModalidadById(id));
				model.addAttribute("paises", paisesService.getAllPaises());
				return "modalidades/edit_modalidad";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/modalidades/{id}")
	public String updateModalidad(@PathVariable Integer id, @ModelAttribute Modalidad modalidad, Model model) {
		// INSERTAR EN BITACORA
		this.validarPerfil();
		Modalidad existingModalidad = modalidadService.getModalidadById(id);
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en modalidades"));

		
		model.addAttribute("paises", paisesService.getAllPaises());
		existingModalidad.setCI_Codigo(id);
		existingModalidad.setCVTitulo(modalidad.getCVTitulo());
		existingModalidad.setCVDescripcion(modalidad.getCVDescripcion());
		existingModalidad.setCVPais(modalidad.getCVPais());
		modalidadService.updateModalidad(existingModalidad);
		return "redirect:/modalidades";
	}

}
