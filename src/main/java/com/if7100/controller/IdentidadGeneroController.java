package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.IdentidadGeneroPais;
import com.if7100.entity.Paises;
import com.if7100.service.BitacoraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.entity.Hecho;
import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Perfil;
import com.if7100.service.IdentidadGeneroService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.IdentidadGeneroPaisService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IdentidadGeneroController {

	private IdentidadGeneroService identidadGeneroService;
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
	private IdentidadGeneroPaisService identidadGeneroPaisService; // Servicio para manejar la relación
	@Autowired
	private PaisesService paisesService; // Servicio para manejar los países

	public IdentidadGeneroController(BitacoraService bitacoraService, IdentidadGeneroService identidadGeneroService,
			PerfilService perfilService, UsuarioRepository usuarioRepository, UsuarioPerfilService usuarioPerfilService,
			UsuarioPerfilRepository usuarioPerfilRepository) {
		super();
		this.identidadGeneroService = identidadGeneroService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
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

	@GetMapping("/identidadgenero")
	public String listStudents(Model model) {
		// model.addAttribute("identidadgenero",
		// identidadGeneroService.getAllIdentidadGenero());
		// return "identidadGeneros/identidadgenero";
		this.validarPerfil();
		return "redirect:/identidadesgenero/1";
	}

	@GetMapping("identidadesgenero/{pg}")
	public String listIdentidadesGeneros(Model model, @PathVariable Integer pg) {

		this.validarPerfil();
	
		if (pg < 1) {
			return "redirect:/identidadesgenero/1";
		}
	
		// Obtener el código de país del usuario logueado
		Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();
	
		// obtiene el id del pais del hecho segun el pais de la organizacion del usuario
        List<IdentidadGenero> identidadGeneroFiltrados = identidadGeneroService.getIdentidadesGeneroByCodigoPais(codigoPaisUsuario);

		  int numeroTotalElementos = identidadGeneroFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<IdentidadGenero> identidadGeneroPaginados = identidadGeneroFiltrados.stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();
	
		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("identidadesGenero", identidadGeneroPaginados);
		
		return "identidadGeneros/identidadgenero";
	}
	

	@GetMapping("/identidadgenero/new")
	public String createIdentidadGenero(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				IdentidadGenero identidadgenero = new IdentidadGenero();
				List<Paises> listaPaises = paisesService.getAllPaises();
				
				model.addAttribute("identidadgenero", identidadgenero);
				model.addAttribute("listaPaises", listaPaises);

				return "identidadGeneros/crear_identidad";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/identidadgenero")
	public String saveIdentidadGenero(@ModelAttribute IdentidadGenero identidadgenero,
			@RequestParam List<String> paisesSeleccionados) {

		identidadGeneroService.saveIdentidadGenero(identidadgenero);

		// Guardar la relación entre identidad de género y países seleccionados
		for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				IdentidadGeneroPais relacion = new IdentidadGeneroPais();
				relacion.setIdentidadGenero(identidadgenero);
				relacion.setPais(pais);
				identidadGeneroPaisService.saveIdentidadGeneroPais(relacion);
			}
		}
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en identidadgenero", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/identidadgenero";
	}

	@GetMapping("/identidadgenero/edit/{id}")
	public String editIdentidadGenero(@PathVariable Integer id, Model model) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

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
			@ModelAttribute IdentidadGenero identidadgenero, Model model) {
		IdentidadGenero existingIdentidadGenero = identidadGeneroService.getIdentidadGeneroById(id);
		existingIdentidadGenero.setId(id);
		existingIdentidadGenero.setNombre(identidadgenero.getNombre());
		existingIdentidadGenero.setDescripcion(identidadgenero.getDescripcion());
		// existingIdentidadGenero.setCodigoPais(identidadgenero.getCodigoPais());
		identidadGeneroService.updateIdentidadGenero(identidadgenero);
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en identidadgenero", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/identidadgenero";
	}

	@GetMapping("/identidadgenero/{Id}")
	public String deleteidentidadgenero(@PathVariable Integer Id) {
		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en identidadgenero", this.usuario.getOrganizacion().getCodigoPais()));

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