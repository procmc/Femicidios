package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.service.BitacoraService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.if7100.entity.Organismo;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.OrganismoService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoOrganismoService;
import com.if7100.service.UsuarioPerfilService;

@Controller
public class OrganismoController {

	@Autowired
	private PaisesService paisesService;
	private UsuarioPerfilService usuarioPerfilService;
	private OrganismoService organismoService;
	private TipoOrganismoService tipoOrganismoService;

	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	public OrganismoController(BitacoraService bitacoraService,
			OrganismoService organismoService, TipoOrganismoService tipoOrganismoService, PerfilService perfilService,
			UsuarioRepository usuarioRepository, UsuarioPerfilService usuarioPerfilService,
			UsuarioPerfilRepository usuarioPerfilRepository) {
		super();
		this.organismoService = organismoService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.tipoOrganismoService = tipoOrganismoService;
		this.bitacoraService = bitacoraService;
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

	@GetMapping("/organismos")
	public String listOrganismos(Model model) {
        this.validarPerfil();
		return "redirect:/organismo/1";
	}

	@GetMapping("/organismo/{pg}")
	public String listOrganismo(Model model, @PathVariable Integer pg) {

		this.validarPerfil();

		// Obtener el código de país del usuario logueado
		Integer codigoPaisUsuarioLogueado = this.usuario.getOrganizacion().getCodigoPais();
		// Buscar el país por el código del país almacenado en Hecho
		Paises pais = paisesService.getPaisByID(codigoPaisUsuarioLogueado);

		List<Organismo> organismosFiltrados = organismoService.findByCodigoPais(codigoPaisUsuarioLogueado);

		int numeroTotalElementos = organismoService.getAllOrganismos().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<Organismo> organismosPaginados = organismosFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("organismos", organismosPaginados);
		model.addAttribute("nombrePais", pais.getSpanish());
		return "organismos/organismos";
	}

	@GetMapping("/organismos/new")
	public String createOrganismoForm(Model model) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("organismo", new Organismo());
				model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());

				// Obtener lista de países y enviarla al modelo
				List<Paises> paises = paisesService.getAllPaises();
				model.addAttribute("paises", paises);

				return "organismos/create_organismo";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/organismos")
	public String savOrganismo(@ModelAttribute Organismo organismo) {
        this.validarPerfil();
		if (!organismo.getCVNombre().equals("") && !organismo.getCVRol().equals("") &&
				!organismo.getCVContacto().equals("")) {
			organismoService.saveOrganismo(organismo);

			bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
					this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en organismos"));
			return "redirect:/organismos";
		}

		return "redirect:/organismos";

	}

	@GetMapping("/organismos/{id}")
	public String deleteOrganismo(@PathVariable int id) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en organismos"));

				organismoService.deleteOrganismoById(id);
				return "redirect:/organismos";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/organismos/edit/{id}")
	public String editOrganismoForm(Model model, @PathVariable int id) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				List<Paises> paises = paisesService.getAllPaises(); // Obtiene la lista de países
				model.addAttribute("paises", paises); // Envía la lista de países al modelo

				model.addAttribute("organismo", organismoService.getOrganismoById(id));
				model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
				return "organismos/edit_organismo";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/organismos/{id}")
	public String updateOrganismo(@PathVariable int id, @ModelAttribute Organismo organismo, Model model) {
		this.validarPerfil();
		Organismo existingOrganismo = organismoService.getOrganismoById(id);
		model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());

		existingOrganismo.setCodigoPais(organismo.getCodigoPais());// actualiza codigo pais
		existingOrganismo.setCI_Id(id);
		existingOrganismo.setCVNombre(organismo.getCVNombre());
		existingOrganismo.setCVRol(organismo.getCVRol());
		existingOrganismo.setCVTipo_Organismo(organismo.getCVTipo_Organismo());
		existingOrganismo.setCVContacto(organismo.getCVContacto());

		organismoService.updateOrganismo(existingOrganismo);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en organismos"));

		return "redirect:/organismos";
	}

}
