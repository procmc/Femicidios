package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.ModalidadesPaises;
import com.if7100.entity.relacionesEntity.OrientacionesSexualesPaises;
import com.if7100.entity.Modalidad;
import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.ModalidadService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.ModalidadesPaisesService;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ModalidadController {

	private ModalidadService modalidadService;
	private UsuarioPerfilService usuarioPerfilService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	private PaisesService paisesService;

	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;
	private Paises paises;
	private ModalidadesPaisesService modalidadesPaisesService;

	public ModalidadController(ModalidadService modalidadService, PerfilService perfilService,
			UsuarioRepository usuarioRepository,
			BitacoraService bitacoraService, PaisesService paisesService, UsuarioPerfilService usuarioPerfilService,
			UsuarioPerfilRepository usuarioPerfilRepository, ModalidadesPaisesService modalidadesPaisesService) {
		super();
		this.modalidadService = modalidadService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.paisesService = paisesService;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;
		this.modalidadesPaisesService = modalidadesPaisesService;
	}

	private void validarPerfil() {

		try {
			Usuario usuario = new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));
			
			List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilRepository.findByUsuario(this.usuario);

            this.perfil = new Perfil(
                    perfilService.getPerfilById(usuarioPerfiles.get(0).getPerfil().getCI_Id()));

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

		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		// obtiene el id del pais del hecho segun el pais de la organizacion del usuario
		List<Modalidad> modalidadFiltrados = modalidadService
				.getModalidadByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = modalidadFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Modalidad> modalidadPaginados = modalidadFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("modalidades", modalidadPaginados);
		return "modalidades/modalidades";
	}

	@GetMapping("/modalidades/new")
	public String createModalidadForm(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				Modalidad modalidad = new Modalidad();
				model.addAttribute("modalidad", modalidad);
				model.addAttribute("listaPaises", paisesService.getAllPaises());

				return "modalidades/create_modalidad";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}

	}

	@PostMapping("/modalidades")
	public String saveModalidad(@ModelAttribute Modalidad modalidad, @RequestParam List<String> paisesSeleccionados) {
		// INSERTAR EN BITACORA
		this.validarPerfil();

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en modalidades", this.usuario.getOrganizacion().getCodigoPais()));

		modalidadService.saveModalidad(modalidad);

		for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				ModalidadesPaises relacion = new ModalidadesPaises();
				relacion.setModalidad(modalidad);
				relacion.setPais(pais); 
				modalidadesPaisesService.saveModalidadesPaises(relacion);
			}
		}

		return "redirect:/modalidades";
	}

	@GetMapping("/modalidades/{id}")
	public String deleteModalidad(@PathVariable Integer id) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				// INSERTAR EN BITACORA
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en modalidades", this.usuario.getOrganizacion().getCodigoPais()));
				
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
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("modalidad", modalidadService.getModalidadById(id));
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
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en modalidades", this.usuario.getOrganizacion().getCodigoPais()));

		
		model.addAttribute("paises", paisesService.getAllPaises());
		existingModalidad.setCI_Codigo(id);
		existingModalidad.setCVTitulo(modalidad.getCVTitulo());
		existingModalidad.setCVDescripcion(modalidad.getCVDescripcion());
		modalidadService.updateModalidad(existingModalidad);
		return "redirect:/modalidades";
	}

}
