/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.*;
/**
 * @author Adam Smasher
 */
import com.if7100.service.*;

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

import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class TipoOrganismoController {
	private TipoOrganismoService tipoOrganismoService;
	private UsuarioPerfilService usuarioPerfilService;
	private PaisesService paisesService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
	private Perfil perfil;
	private Paises paises;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	public TipoOrganismoController(BitacoraService bitacoraService,
			TipoOrganismoService tipoOrganismoService, PaisesService paisesService, PerfilService perfilService,
			UsuarioRepository usuarioRepository,
			UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository) {
		super();
		this.tipoOrganismoService = tipoOrganismoService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.paisesService = paisesService;
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

	@GetMapping("/tipoOrganismo")
	public String listStudents(Model model) {
		this.validarPerfil();
		return "redirect:/tipoorganismo/1";
	}

	@GetMapping("/tipoorganismo/{pg}")
	public String listOrganismo(Model model, @PathVariable Integer pg) {
		this.validarPerfil();
		if (pg < 1) {
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
	public String CreateTipoOrganismoForm(Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("tipoOrganismo", new TipoOrganismo());
				model.addAttribute("paises", paisesService.getAllPaises());
				return "tipoOrganismo/create_tipoOrganismo";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/tipoOrganismo")
	public String saveTipoOrganismo(@ModelAttribute TipoOrganismo tipoOrganismo) {
		this.validarPerfil();

		tipoOrganismoService.saveTipoOrganismo(tipoOrganismo);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en tipos de organismos", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/tipoOrganismo";
	}

	@GetMapping("/tipoOrganismo/{Codigo}")
	public String deleteTipoOrganismoByCodigo(@PathVariable Integer Codigo) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
						this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipos de organismos", this.usuario.getOrganizacion().getCodigoPais()));

				tipoOrganismoService.deleteTipoOrganismoByCodigo(Codigo);
				return "redirect:/tipoOrganismo";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/tipoOrganismo/edit/{Codigo}")
	public String editTipoOrganismoForm(@PathVariable Integer Codigo, Model model) {

		try {
			this.validarPerfil();
			if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
					|| usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("tipoOrganismo", tipoOrganismoService.getTipoOrganismoByCodigo(Codigo));
				model.addAttribute("paises", paisesService.getAllPaises());
				return "tipoOrganismo/edit_tipoOrganismo";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/tipoOrganismo/{Codigo}")
	public String updateOrganismoLugar(@PathVariable Integer Codigo, @ModelAttribute TipoOrganismo tipoOrganismo,
			Model model) {
		this.validarPerfil();
		TipoOrganismo existingTipoOrganismo = tipoOrganismoService.getTipoOrganismoByCodigo(Codigo);
		model.addAttribute("paises", paisesService.getAllPaises());

		existingTipoOrganismo.setCI_Codigo(Codigo);
		existingTipoOrganismo.setCVTitulo(tipoOrganismo.getCVTitulo());
		existingTipoOrganismo.setCVDescripcion(tipoOrganismo.getCVDescripcion());
		existingTipoOrganismo.setCVPaises(tipoOrganismo.getCVPaises());
		tipoOrganismoService.updateTipoOrganismo(existingTipoOrganismo);

		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en tipos de organismos", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/tipoOrganismo";
	}
}
