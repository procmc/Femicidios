package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.*;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import com.if7100.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LugarController {

	private LugarService lugarService;
	private TipoLugarService tipoLugarService;

	private HechoService hechoService;

	private PaisesService paisesService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	public LugarController(BitacoraService bitacoraService,PaisesService paisesService, LugarService lugarService,
			TipoLugarService tipoLugarService, HechoService hechoService, PerfilService perfilService,
			UsuarioRepository usuarioRepository) {
		super();
		this.lugarService = lugarService;
		this.paisesService = paisesService;
		this.tipoLugarService = tipoLugarService;
		this.hechoService = hechoService;
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService = bitacoraService;

	}

	private void validarPerfil() {

		try {
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


//	// Mostrar todos lugares
//	@GetMapping("/lugar/{Id}")
//	public String listStudents(Model model, @PathVariable Integer Id, HttpSession session) {
//		session.setAttribute("idLugarHecho", Id);
//		List<Lugar> listaLugar = lugarService.getAllLugares(Id);
//		model.addAttribute("lugar", listaLugar);
//		return "lugares/lugares";
//	}

	// Eliminar Lugar
	@GetMapping("/lugares/{Id}")
	public String deleteLugar(@PathVariable Integer Id, HttpSession session) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				String descripcion = "Elimino un lugar: " + Id;
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
				bitacoraService.saveBitacora(bitacora);
				lugarService.deleteLugarById(Id);
				return "redirect:/lugares";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/hecholugares/{id}/{idLugar}")
	public String deleteLugar(@PathVariable Integer id, @PathVariable Integer idLugar) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				lugarService.deleteLugarById(id);
				String descripcion = "Elimino un lugar: "+ id;
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				return "redirect:/hecholugar/".concat(String.valueOf(idLugar));
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	// Editar Lugar
	@GetMapping("/lugares/edit/{Id}")
	public String editLugarForm(@PathVariable Integer Id, Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("lugar", lugarService.getLugarById(Id));
				model.addAttribute("paises", paisesService.getAllPaises());
				model.addAttribute("hechos", hechoService.getAllHechos());
				model.addAttribute("tiposLugares", tipoLugarService.getAllTipoLugares());
				return "lugares/edit_lugar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/lugares/{id}")
	public String updateLugar(@PathVariable Integer id, @ModelAttribute("lugar") Lugar lugar, Model model) {
		try {
			Lugar existingLugar = lugarService.getLugarById(id);
			existingLugar.setCI_Codigo(id);
			existingLugar.setCIHecho(lugar.getCIHecho());
			existingLugar.setCV_Descripcion(lugar.getCV_Descripcion());
			existingLugar.setCITipoLugar(lugar.getCITipoLugar());
			existingLugar.setCV_Direccion(lugar.getCV_Direccion());
			existingLugar.setCV_Ciudad(lugar.getCV_Ciudad());
			existingLugar.setCI_Pais(lugar.getCI_Pais());
			existingLugar.setCI_Codigo_Postal(lugar.getCI_Codigo_Postal());
			lugarService.updateLugar(existingLugar);
			String descripcion="Actualizo en Lugares el id: "+ id;
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
			return "redirect:/lugares";
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return editLugarForm(id, model);
		}
	}

//	@PostMapping("/lugares")
//	public String saveLugar(@ModelAttribute("lugar") Lugar lugar, Model model) {
//		try {
//			lugarService.saveLugar(lugar);
//			String descripcion="Creo en Lugar";
//            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
//            bitacoraService.saveBitacora(bitacora);
//			return "redirect:/lugar/" + lugar.getCIHecho();
//		} catch (DataIntegrityViolationException e) {
//			String mensaje = "No se puede guardar el lugar debido a un error de integridad de datos.";
//			model.addAttribute("error_message", mensaje);
//			model.addAttribute("error", true);
//			return createLugarForm(model, lugar.getCIHecho());
//		}
//	}

	// ESTE METODO ESTA BUENO PERO SOLO CUANDO ENTRA POR VER LUGARES// ESTE SI ESTA
	// PROBADO
	// Nuevo Lugar
//	@GetMapping("/lugares/new/{Id}")
//	public String createLugarForm(Model model, @PathVariable Integer Id) {
//
//		try {
//			this.validarPerfil();
//			if (!this.perfil.getCVRol().equals("Consulta")) {
//
//				Lugar lugar = new Lugar();
//				lugar.setCIHecho(Id);
//				model.addAttribute("lugar", lugar);
//				model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
//				return "lugares/create_lugar";
//			} else {
//				return "SinAcceso";
//			}
//
//		} catch (Exception e) {
//			return "SinAcceso";
//		}
//	}

	@GetMapping("/lugares")
	public String listLugares(Model model) {
		return "redirect:/lugar/1";
	}

	@GetMapping("/lugar/{pg}")
	public String listLugar(Model model, @PathVariable Integer pg){
		if (pg < 1){
			return "redirect:/lugar/1";
		}

		int numeroTotalElementos = lugarService.getAllLugar().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		Page<Lugar> lugarPage = lugarService.getAllLugarPage(pageable);

		List<Integer> nPaginas = IntStream.rangeClosed(1, lugarPage.getTotalPages())
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("lugares", lugarPage.getContent());
		model.addAttribute("lugar", true);
		model.addAttribute("tipoLugares", lugarService.getAllTipoLugars());
		return "lugares/lugares";
	}

	@GetMapping("/hecholugar/{id}")
	public String listHechoLugares(Model model, @PathVariable Integer id) {
		return "redirect:/hecholugar/".concat(String.valueOf(id)).concat("/1");
	}

	@GetMapping("/hecholugar/{id}/{pg}")
	public String listHechoLugar(Model model, @PathVariable Integer id, @PathVariable Integer pg){
		if (pg < 1){
			return "redirect:/hecholugar/".concat(String.valueOf(id)).concat("/1");
		}

//		int numeroTotalElementos = lugarService.getAllLugares(id).size();

//		Pageable pageable = initPages(pg, 5, numeroTotalElementos);
//
//		Page<Lugar> lugarPage = lugarService.getAllLugaresPage(pageable, id);



		//List<Integer> nPaginas = IntStream.rangeClosed(1, lugarPage.getTotalPages()).boxed().toList();

		model.addAttribute("Id", id);
		model.addAttribute("PaginaActual", pg);
//		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("lugares", lugarService.getAllLugares(id));
		model.addAttribute("hecholugar", true);
		return "lugares/lugares";
	}



	// Este Metodo esta bueno pero solo sirve si selecciona ver la lista de lugares
	// de un hecho y desde la misma ventana de lugar se agrega un lugar de un hecho
	// en especifico

//Nuevo Lugar
	@GetMapping("/lugares/new")
	public String createLugarForm(Model model) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				Lugar lugar = new Lugar();
				model.addAttribute("lugar", lugar);
				model.addAttribute("paises", paisesService.getAllPaises());
				model.addAttribute("hechos", hechoService.getAllHechos());
				model.addAttribute("tiposLugares", tipoLugarService.getAllTipoLugares());
				return "lugares/create_lugar";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/lugar")
	public String saveLugar (@ModelAttribute("lugar") Lugar lugar, Model model) {
		try {
			lugarService.saveLugar(lugar);
			String descripcion="Creo en Lugar:" + lugar.getCI_Codigo();
			Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
			bitacoraService.saveBitacora(bitacora);
			return "redirect:/lugares";
		} catch (DataIntegrityViolationException e){
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return createLugarForm(model);
		}

	}

	@PostMapping("/hecholugar")
	public String saveHechoLugar (@ModelAttribute("lugar") Lugar lugar, Model model) {
		try {
			lugarService.saveLugar(lugar);
			String descripcion="Creo en HechoLugar: "+ lugar.getCI_Codigo();;
			Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
			bitacoraService.saveBitacora(bitacora);
			return "redirect:/hecholugar/".concat(String.valueOf(lugar.getCIHecho()));
		} catch (DataIntegrityViolationException e){
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return createHechoLugarForm(model, lugar.getCIHecho());
		}

	}

	@GetMapping("/hecholugar/new/{Id}")
	public String createHechoLugarForm(Model model, @PathVariable Integer Id){
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				Lugar lugar = new Lugar();
				lugar.setCIHecho(Id);
				model.addAttribute("lugar", lugar);
				model.addAttribute("paises", paisesService.getAllPaises());
				model.addAttribute("hechos", hechoService.getAllHechos());
				model.addAttribute("tiposLugares", tipoLugarService.getAllTipoLugares());
				return "lugares/create_lugares";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}

	}

}
