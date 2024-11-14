package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.OrientacionesSexualesPaises;
import com.if7100.service.BitacoraService;

import com.if7100.entity.Hecho;

import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.OrientacionSexualService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.OrientacionesSexualesPaisesService;

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

import com.if7100.entity.OrientacionSexual;
import com.if7100.service.OrientacionSexualService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrientacionSexualController {

	private OrientacionSexualService orientacionService;
	private UsuarioPerfilService usuarioPerfilService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;
	private PaisesService paisesService; // Servicio para manejar los países
	private OrientacionesSexualesPaisesService orientacionesSexualesPaisesService;

	public OrientacionSexualController(BitacoraService bitacoraService,
			OrientacionSexualService orientacionService, PerfilService perfilService,
			UsuarioRepository usuarioRepository, UsuarioPerfilService usuarioPerfilService,
			UsuarioPerfilRepository usuarioPerfilRepository,
			OrientacionesSexualesPaisesService orientacionesSexualesPaisesService, PaisesService paisesService) {
		super();
		this.orientacionService = orientacionService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;
		this.orientacionesSexualesPaisesService = orientacionesSexualesPaisesService;
		this.paisesService = paisesService;
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

	@GetMapping("/orientacionesSexuales")
	public String listOrientacionesSexuales(Model model) {
		this.validarPerfil();
		return "redirect:/orientacionessexuales/1";
	}

	@GetMapping("/orientacionessexuales/{pg}")
	public String listOrientacionesSexuales(Model model, @PathVariable Integer pg) {
		this.validarPerfil();
		if (pg < 1) {
			return "redirect:/orientacionessexuales/1";
		}

		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		// obtiene el id del pais del hecho segun el pais de la organizacion del usuario
		List<OrientacionSexual> orientacionSexualFiltrados = orientacionService
				.getOrientacionSexualByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = orientacionSexualFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<OrientacionSexual> orientacionSexualPaginados = orientacionSexualFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("orientacionessexuales", orientacionSexualPaginados);
		return "orientacionesSexuales/orientacionesSexuales";
	}

	@GetMapping("/orientacionessexuales/new")
	public String createOrientacionSexualForm(Model model) {
		this.validarPerfil();
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("orientacionSexual", new OrientacionSexual());
				model.addAttribute("listaPaises", paisesService.getAllPaises());

				return "orientacionesSexuales/create_orientacionSexual";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			System.out.println("Erro cr1 " + e);
			return "SinAcceso";
		}
	}

	// @GetMapping("/orientacionesSexuales")
	// public String listStudents(Model model) {
	//
	// model.addAttribute("orientacionesSexuales",orientacionService.getAllOrientacionesSexuales());
	// return "orientacionessexuales/orientacionesSexuales";
	// }

	@GetMapping("/orientacionesSexuales/new")
	public String createUsuarioForm(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("orientacion", new OrientacionSexual());
				model.addAttribute("listaPaises", paisesService.getAllPaises());

				return "orientacionesSexuales/create_orientacionesSexuales";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			System.out.println("Erro cr2 " + e);

			return "SinAcceso";
		}
	}

	@PostMapping("/orientacionesSexuales")
	public String saveOrientacion(@ModelAttribute OrientacionSexual orientacion,
			@RequestParam List<String> paisesSeleccionados) {
		this.validarPerfil();
		orientacionService.saveOrientacionSexual(orientacion);

		for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				OrientacionesSexualesPaises relacion = new OrientacionesSexualesPaises();
				relacion.setOrientacionSexual(orientacion);
				relacion.setPais(pais);
				orientacionesSexualesPaisesService.saveOrientacionesSexualesPaises(relacion);
			}
		}

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en orientacion sexual",
				this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/orientacionesSexuales";
	}

	@GetMapping("/orientacionesSexuales/{id}")
	public String deleteOrientacion(@PathVariable int id) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				orientacionService.getOrientacionSexualByCodigo(id).getCVTitulo();

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en orientacion sexual",
						this.usuario.getOrganizacion().getCodigoPais()));

				orientacionService.deleteOrientacionSexualByCodigo(id);
				return "redirect:/orientacionesSexuales";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/orientacionesSexuales/edit/{id}")
	public String editOrientacionForm(Model model, @PathVariable int id) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("orientacion", orientacionService.getOrientacionSexualByCodigo(id));
				return "orientacionesSexuales/edit_orientacionesSexuales";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/orientacionesSexuales/{id}")
	public String updateOrientacionSexual(@PathVariable int id, @ModelAttribute OrientacionSexual orientacion,
			Model model) {
		this.validarPerfil();
		OrientacionSexual existingOrientacion = orientacionService.getOrientacionSexualByCodigo(id);
		String orientacionAnt = existingOrientacion.getCVTitulo();
		existingOrientacion.setCI_Codigo(id);
		existingOrientacion.setCVTitulo(orientacion.getCVTitulo());
		existingOrientacion.setCVDescripcion(orientacion.getCVDescripcion());

		orientacionService.updateOrientacionSexual(existingOrientacion);
		if (orientacionAnt.equals(orientacionService.getOrientacionSexualByCodigo(id).getCVTitulo())) {
			orientacionService.getOrientacionSexualByCodigo(id).getCVTitulo();

			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
					this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en orientacion sexual",
					this.usuario.getOrganizacion().getCodigoPais()));

		} else {
			orientacionService.getOrientacionSexualByCodigo(id).getCVTitulo();
			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
					this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en orientacion sexual",
					this.usuario.getOrganizacion().getCodigoPais()));

		}

		return "redirect:/orientacionesSexuales";
	}

}
