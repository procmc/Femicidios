/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Organizacion;
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;
import com.if7100.service.OrganizacionService;
import com.if7100.service.PaisesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;

import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UsuarioController {
	@Autowired

	private UsuarioService usuarioService;

	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	@Autowired
	private PaisesService paisesService;
	private OrganizacionService organizacionService;

	// prueba
	// private SessionRegistry sessionRegistry;

	public UsuarioController(BitacoraService bitacoraService,
			UsuarioService usuarioService, PerfilService perfilService, UsuarioRepository usuarioRepository,
			OrganizacionService organizacionService) {
		super();
		this.usuarioService = usuarioService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;
		this.organizacionService = organizacionService;
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

	@GetMapping("/usuarios")
	public String listStudents(Model model) {
		return "redirect:/usuario/1";
	}

	@GetMapping("/usuario/{pg}")
	public String listUsuario(Model model, @PathVariable Integer pg) {
		try {
			this.validarPerfil();
			if (this.perfil.getCVRol().equals("Administrador")) {

				// Obtener el código de país del usuario logueado
				Integer codigoPaisUsuarioLogueado = this.usuario.getCodigoPais();

				// Filtrar usuarios según el código de país almacenado en la organización
				List<Usuario> usuariosFiltrados = usuarioService.getAllUsuarios().stream()
				.filter(usuario -> usuario.getOrganizacion() != null && 
								   usuario.getOrganizacion().getCodigoPais().equals(codigoPaisUsuarioLogueado))
				.collect(Collectors.toList());

				// Paginación
				int numeroTotalElementos = usuariosFiltrados.size();

				Pageable pageable = initPages(pg, 5, numeroTotalElementos);

				int tamanoPagina = pageable.getPageSize();
				int numeroPagina = pageable.getPageNumber();

				List<Usuario> usuariosPaginados = usuariosFiltrados.stream()
						.skip((long) numeroPagina * tamanoPagina)
						.limit(tamanoPagina)
						.collect(Collectors.toList());

				List<Integer> nPaginas = IntStream
						.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
						.boxed()
						.toList();
				// Obtener la organización asociada al usuario
				Organizacion organizacion = usuario.getOrganizacion();
				
				// Enviar datos al modelo
				model.addAttribute("PaginaActual", pg);
				model.addAttribute("nPaginas", nPaginas);
				model.addAttribute("usuarios", usuariosPaginados);
				model.addAttribute("organizacion", organizacion);

				return "usuarios/usuarios";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			System.out.println("ver el error " + e);
			return "SinAcceso";
		}
	}

	// creacion de un nuevo usuario
	@GetMapping("/usuarios/new")
	public String createUsuarioForm(Model model) {

		try {
			this.validarPerfil();
			if (this.perfil.getCVRol().equals("Administrador")) {

				Usuario usuario = new Usuario();
				model.addAttribute("usuario", usuario);

				// Obtener lista de países y enviarla al modelo
				List<Paises> paises = paisesService.getAllPaises();
				model.addAttribute("paises", paises);

				// Obtener lista de países y enviarla al modelo
				List<Organizacion> organizacion = organizacionService.getAllOrganizacion();
				model.addAttribute("organizacion", organizacion);

				return "usuarios/create_usuario";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/usuarios")
	public String saveUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			return "usuarios/create_usuario";
		} else {
			usuarioService.saveUsuario(usuario);

			String descripcion = "Creo un Nuevo Usuario con id: " + usuario.getCI_Id();
			Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),
					this.perfil.getCVRol(), descripcion);
			bitacoraService.saveBitacora(bitacora);

			return "redirect:/usuarios";
		}
	}

	// Eliminar Usuario
	@GetMapping("/usuarios/{Id}")
	public String deleteUsuario(@PathVariable Integer Id) {

		try {
			this.validarPerfil();
			if (this.perfil.getCVRol().equals("Administrador")) {

				String descripcion = "Elimino un usuario con id: " + Id;
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),
						this.perfil.getCVRol(), descripcion);
				bitacoraService.saveBitacora(bitacora);

				usuarioService.deleteUsuarioById(Id);
				return "redirect:/usuarios?Exito";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	// EditarUsuario
	@GetMapping("/usuarios/edit/{Id}")
	public String editUsuarioForm(@PathVariable Integer Id, Model model) {

		try {
			this.validarPerfil();
			if (this.perfil.getCVRol().equals("Administrador")) {

				List<Paises> paises = paisesService.getAllPaises();
				model.addAttribute("paises", paises); 

				model.addAttribute("organizacion", organizacionService.getAllOrganizacion()); 

				model.addAttribute("usuario", usuarioService.getUsuarioById(Id));
				return "usuarios/edit_usuario";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/usuarios/{Id}")
	public String updateUsuario(@PathVariable Integer Id, @ModelAttribute Usuario usuario) {
		Usuario existingUsuario = usuarioService.getUsuarioById(Id);
		existingUsuario.setCI_Id(Id);
		existingUsuario.setCVCedula(usuario.getCVCedula());
		existingUsuario.setCVNombre(usuario.getCVNombre());
		existingUsuario.setCVApellidos(usuario.getCVApellidos());
		existingUsuario.setCIPerfil(usuario.getCIPerfil());
		existingUsuario.setTCClave(usuario.getTCClave());
		existingUsuario.setCodigoPais(usuario.getCodigoPais()); // Actualiza el país seleccionado
		existingUsuario.setOrganizacion(usuario.getOrganizacion());

		usuarioService.updateUsuario(existingUsuario);
		String descripcion = "Actualizo un Usuario con id: " + Id;
		Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(),
				descripcion);
		bitacoraService.saveBitacora(bitacora);
		return "redirect:/usuarios";
	}
}
