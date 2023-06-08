package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;
import java.util.List;
import com.if7100.service.HechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import com.if7100.entity.Lugar;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.LugarService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoLugarService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LugarController {

	@Autowired
	private LugarService lugarService;
	private TipoLugarService tipoLugarService;

	private HechoService hechoService;
	// instancias para control de acceso
	private UsuarioRepository usuarioRepository;
	private Perfil perfil;
	private PerfilService perfilService;
	// instancias para control de bitacora
	private BitacoraService bitacoraService;
	private Usuario usuario;

	public LugarController(BitacoraService bitacoraService, LugarService lugarService,
			TipoLugarService tipoLugarService, HechoService hechoService, PerfilService perfilService,
			UsuarioRepository usuarioRepository) {
		super();
		this.lugarService = lugarService;
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

	// Mostrar todos lugares
	@GetMapping("/lugar/{Id}")
	public String listStudents(Model model, @PathVariable Integer Id, HttpSession session) {
		session.setAttribute("idLugarHecho", Id);
		List<Lugar> listaLugar = lugarService.getAllLugares(Id);
		model.addAttribute("lugar", listaLugar);
		return "lugares/lugares";
	}

	// Eliminar Lugar
	@GetMapping("/lugar/eliminar/{Id}")
	public String deleteLugar(@PathVariable Integer Id, HttpSession session) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
			/*	String descripcion = "Elimino un lugar";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion,
						this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);*/

				Integer idLugarHecho = (Integer) session.getAttribute("idLugarHecho");
				lugarService.deleteLugarById(Id);
				return "redirect:/lugar/" + idLugarHecho;
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	// Editar Lugar
	@GetMapping("/lugar/edit/{Id}")
	public String editLugarForm(@PathVariable Integer Id, Model model) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("lugar", lugarService.getLugarById(Id));
				model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
				return "lugares/edit_lugar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/lugar/{id}")
	public String updateLugar(@PathVariable Integer id, @ModelAttribute("lugar") Lugar lugar, Model model) {
		try {
			Lugar existingLugar = lugarService.getLugarById(id);
			existingLugar.setCI_Codigo(id);
			existingLugar.setCIHecho(existingLugar.getCIHecho());
			existingLugar.setCV_Descripcion(lugar.getCV_Descripcion());
			existingLugar.setCI_Tipo_Lugar(lugar.getCI_Tipo_Lugar());
			existingLugar.setCV_Direccion(lugar.getCV_Direccion());
			existingLugar.setCV_Ciudad(lugar.getCV_Ciudad());
			existingLugar.setCI_Pais(lugar.getCI_Pais());
			existingLugar.setCI_Codigo_Postal(lugar.getCI_Codigo_Postal());
			lugarService.updateLugar(existingLugar);
			String descripcion="Actualizo en Lugares";
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
			return "redirect:/lugar/" + existingLugar.getCIHecho();
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return editLugarForm(id, model);
		}
	}

	@PostMapping("/lugar")
	public String saveLugar(@ModelAttribute("lugar") Lugar lugar, Model model) {
		try {
			lugarService.saveLugar(lugar);
			String descripcion="Creo en Lugar";
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
			return "redirect:/lugar/" + lugar.getCIHecho();
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede guardar el lugar debido a un error de integridad de datos.";
			model.addAttribute("error_message", mensaje);
			model.addAttribute("error", true);
			return createLugarForm(model, lugar.getCIHecho());
		}
	}

	// ESTE METODO ESTA BUENO PERO SOLO CUANDO ENTRA POR VER LUGARES// ESTE SI ESTA
	// PROBADO
	// Nuevo Lugar
	@GetMapping("/lugar/new/{Id}")
	public String createLugarForm(Model model, @PathVariable Integer Id) {

		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {

				Lugar lugar = new Lugar();
				lugar.setCIHecho(Id);
				model.addAttribute("lugar", lugar);
				model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
				return "lugares/create_lugar";
			} else {
				return "SinAcceso";
			}

		} catch (Exception e) {
			return "SinAcceso";
		}
	}

	@GetMapping("/lugares")
	public String listLugares(Model model) {
		List<Lugar> listaLugar = lugarService.getAllLugar();
		model.addAttribute("lugar", listaLugar);
		return "lugares/lugares";
	}

	@GetMapping("/lugar")
	public String listLugares(@RequestParam("id") Integer id, Model model) {
		List<Lugar> listaLugar = lugarService.getAllLugares(id);
		model.addAttribute("lugar", listaLugar);
		return "lugares/lugares";
	}

	// Este Metodo esta bueno pero solo sirve si selecciona ver la lista de lugares
	// de un hecho y desde la misma ventana de lugar se agrega un lugar de un hecho
	// en especifico

//Nuevo Lugar
	@GetMapping("/lugares/new")
	public String createHechoForm(Model model) {
		try {
			this.validarPerfil();
			if (!this.perfil.getCVRol().equals("Consulta")) {
				Lugar lugar = new Lugar();
				model.addAttribute("lugar", lugar);
				model.addAttribute("hecho", hechoService.getAllHechos());
				model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
				return "lugares/create_lugares";
			} else {
				return "SinAcceso";
			}
		} catch (Exception e) {
			return "SinAcceso";
		}
	}

}
