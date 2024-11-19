package com.if7100.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.if7100.entity.Bitacora;

import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.TipoRelacionFamiliarPaises;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionFamiliarService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.TipoRelacionFamiliarPaisesService;

@Controller
public class TipoRelacionFamiliarController {

	private TipoRelacionFamiliarService tipoRelacionFamiliarService;
	private UsuarioPerfilService usuarioPerfilService;
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;
	private PaisesService paisesService;
	private TipoRelacionFamiliarPaisesService tipoRelacionFamiliarPaisesService;

	public TipoRelacionFamiliarController(TipoRelacionFamiliarService tipoRelacionFamiliarService,
			UsuarioRepository usuarioRepository, PerfilService perfilService,
			BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository,
			PaisesService paisesService, TipoRelacionFamiliarPaisesService tipoRelacionFamiliarPaisesService) {
		super();
		this.tipoRelacionFamiliarService = tipoRelacionFamiliarService;
		this.usuarioRepository = usuarioRepository;

		this.perfilService = perfilService;
		this.bitacoraService = bitacoraService;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;
		this.paisesService = paisesService;
		this.tipoRelacionFamiliarPaisesService = tipoRelacionFamiliarPaisesService;

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

	@GetMapping("/tiporelacionfamiliar")
	public String listTipoRelacionFamiliar(Model model) {
		// model.addAttribute("identidadgenero",
		// identidadGeneroService.getAllIdentidadGenero());
		// return "identidadGeneros/identidadgenero";
		this.validarPerfil();

		return "redirect:/tiporelacionfamiliar/1";
	}

	@GetMapping("tiporelacionfamiliar/{pg}")
	public String listTipoRelacionFamiliar(Model model, @PathVariable Integer pg) {
		this.validarPerfil();
		if (pg < 1) {
			return "redirect:/tiporelacionfamiliar/1";
		}
		
		 Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		// obtiene el id del pais del hecho segun el pais de la organizacion del usuario
		List<TipoRelacionFamiliar> tipoRelacionFamiliarFiltrados = tipoRelacionFamiliarService
				.getTipoRelacionFamiliarByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = tipoRelacionFamiliarFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<TipoRelacionFamiliar> tipoRelacionFamiliarPaginados = tipoRelacionFamiliarFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("tiporelacionfamiliar", tipoRelacionFamiliarPaginados);
		return "tipoRelacionFamiliar/tiporelacionfamiliar";
	}

	@GetMapping("/tiporelacionfamiliar/new")
	public String createTiporelacionfamiliar(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				TipoRelacionFamiliar tipoRelacionFamiliar = new TipoRelacionFamiliar();
				// Paises paises = new Paises();
				model.addAttribute("tiporelacionfamiliar", tipoRelacionFamiliar);
				model.addAttribute("listaPaises", paisesService.getAllPaises());

				return "tipoRelacionFamiliar/crear_tipo_relacion_familiar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/tiporelacionfamiliar")
	public String saveTiporelacionfamiliar(
			@ModelAttribute("tiporelacionfamiliar") TipoRelacionFamiliar tipoRelacionFamiliar,
			@RequestParam List<String> paisesSeleccionados) {
		this.validarPerfil();

		tipoRelacionFamiliarService.saveTipoRelacionFamiliar(tipoRelacionFamiliar);

		for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				TipoRelacionFamiliarPaises relacion = new TipoRelacionFamiliarPaises();
				relacion.setTipoRelacionFamiliar(tipoRelacionFamiliar);
				relacion.setPais(pais);
				tipoRelacionFamiliarPaisesService.saveTipoRelacionFamiliarPaises(relacion);
			}
		}

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en tipos de relaciones familiares", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/tiporelacionfamiliar";
	}

	@GetMapping("/tiporelacionfamiliar/edit/{id}")
	public String editTipoRelacionFamiliar(@PathVariable Integer id, Model model) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("tiporelacionfamiliar", tipoRelacionFamiliarService.getTipoRelacionFamiliarById(id));

				return "tipoRelacionFamiliar/edit_tiporelacionfamiliar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/tiporelacionfamiliar/{id}")
	public String updateTiporelacionfamiliar(@PathVariable Integer id,
			@ModelAttribute("tiporelacionfamiliar") TipoRelacionFamiliar tipoRelacionFamiliar, Model model) {
		this.validarPerfil();

		TipoRelacionFamiliar existingTipoRelacionFamiliar = tipoRelacionFamiliarService.getTipoRelacionFamiliarById(id);
		existingTipoRelacionFamiliar.setCI_Codigo(id);
		existingTipoRelacionFamiliar.setNombre(tipoRelacionFamiliar.getNombre());
		existingTipoRelacionFamiliar.setDescripcion(tipoRelacionFamiliar.getDescripcion());
		tipoRelacionFamiliarService.updateTipoRelacionFamiliar(existingTipoRelacionFamiliar);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en tipos de relaciones familiares", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/tiporelacionfamiliar";
	}

	@GetMapping("/tiporelacionFamiliar/{Id}")
	public String deleteTiporelacionfamiliar(@PathVariable Integer Id) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(),
						"Elimina en tipos de relaciones familiares", this.usuario.getOrganizacion().getCodigoPais()));

				tipoRelacionFamiliarService.deleteTipoRelacionFamiliarById(Id);
				return "redirect:/tiporelacionfamiliar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}
}
