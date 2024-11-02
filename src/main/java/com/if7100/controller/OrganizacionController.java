package com.if7100.controller;

import org.springframework.stereotype.Controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.Paises;
import com.if7100.service.BitacoraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.entity.Organizacion;
import com.if7100.entity.Perfil;
import com.if7100.service.OrganizacionService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrganizacionController {

	private OrganizacionService organizacionService;
	private UsuarioPerfilService usuarioPerfilService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	@Autowired
	private PaisesService paisesService; // Servicio para manejar los países

	OrganizacionController(OrganizacionService organizacionService, BitacoraService bitacoraService,
			PerfilService perfilService, UsuarioRepository usuarioRepository, UsuarioPerfilService usuarioPerfilService,
			UsuarioPerfilRepository usuarioPerfilRepository) {

		this.organizacionService = organizacionService;
		this.perfilService = perfilService;
		this.bitacoraService = bitacoraService;
		this.usuarioRepository = usuarioRepository;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;
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

	@GetMapping("/organizacion")
	public String listOrganizaciones(Model model) {
		this.validarPerfil();
		return "redirect:/organizacion/1";
	}

	@GetMapping("organizacion/{pg}")
	public String listOrganizacion(Model model, @PathVariable Integer pg) {

		this.validarPerfil();

		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		List<Organizacion> organizacionfiltradas = organizacionService.findByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = organizacionfiltradas.size();
		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		// Buscar el país por el código del país almacenado en Hecho
		Paises pais = paisesService.getPaisByID(codigoPaisUsuario);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Organizacion> OrganizacionPaginados = organizacionfiltradas.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("organizacion", OrganizacionPaginados);
		model.addAttribute("nombrePais", pais.getSpanish());

		return "organizacion/organizacion";
	}

	@GetMapping("/organizacion/new")
	public String createOrganizacion(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				Organizacion organizacion = new Organizacion();
				List<Paises> paises = paisesService.getAllPaises();
				model.addAttribute("organizacion", organizacion);
				model.addAttribute("paises", paises);

				return "organizacion/create_organizacion";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/organizacion")
	public String saveOrganizacion(@ModelAttribute Organizacion organizacion) {
		this.validarPerfil();
		organizacion.setCodigoPais(this.usuario.getOrganizacion().getCodigoPais());
		organizacionService.saveOrganizacion(organizacion);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en organizacion"));

		return "redirect:/organizacion";
	}

	@GetMapping("/organizacion/edit/{id}")
	public String editOrganizacion(@PathVariable Integer id, Model model) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				List<Paises> paises = paisesService.getAllPaises(); // Obtiene la lista de países

				model.addAttribute("paises", paises); // Envía la lista de países al modelo
				model.addAttribute("organizacion", organizacionService.getOrganizacionById(id));

				return "organizacion/edit_organizacion";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/organizacion/{id}")
	public String updateOrganizacion(@PathVariable Integer id,
			@ModelAttribute Organizacion organizacion, Model model) {
		this.validarPerfil();

		Organizacion existingOrganizacion = organizacionService.getOrganizacionById(id);

		existingOrganizacion.setCI_Codigo_Organizacion(id);
		existingOrganizacion.setCVNombre(organizacion.getCVNombre());
		existingOrganizacion.setCVDireccion(organizacion.getCVDireccion());
		existingOrganizacion.setCVTelefono(organizacion.getCVTelefono());
		existingOrganizacion.setCVCorreo(organizacion.getCVCorreo());

		organizacionService.updateOrganizacion(existingOrganizacion);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en organizacion"));

		return "redirect:/organizacion";
	}

	@GetMapping("/organizacion/eliminar/{id}")
	public String deleteOrganizacion(@PathVariable Integer id, Model model) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				try {

					bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
							this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en organizacion"));

					organizacionService.deleteOrganizacionById(id);

				} catch (DataIntegrityViolationException e) {

					String mensaje = "Error, No se puede eliminar una organización si tiene usuarios asociados";
					model.addAttribute("error_message", mensaje);
					model.addAttribute("error", true);
					return listOrganizacion(model, 1);
				}
				return "redirect:/organizacion";

			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {

			return "SinAcceso";
		}
	}

}
