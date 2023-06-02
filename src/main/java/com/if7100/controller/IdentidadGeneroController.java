/**
 *
 */
package com.if7100.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.IdentidadGeneroService;
import com.if7100.service.PerfilService;

/**
 * @author michael arauz torrez
 *
 */
@Controller
public class IdentidadGeneroController {

	/**
	 *
	 */
	private IdentidadGeneroService identidadGeneroService;
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;

	public IdentidadGeneroController(BitacoraService bitacoraService,
IdentidadGeneroService identidadGeneroService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
		super();
		this.identidadGeneroService = identidadGeneroService;
		this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService= bitacoraService;

	}

	 private void validarPerfil() {

			try {
				Usuario usuario=new Usuario();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			    String username = authentication.getName();
			    this.usuario= new Usuario(usuarioRepository.findByCVCedula(username));

				this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

			}catch (Exception e) {
				// TODO: handle exception
			}

		}

	@GetMapping("/identidadgenero")
	public String listStudents(Model model) {
		model.addAttribute("identidadgenero", identidadGeneroService.getAllIdentidadGenero());
		return "identidadGeneros/identidadgenero";
	}

	@GetMapping("/identidadgenero/new")
	public String createIdentidadGenero(Model model) {

		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				IdentidadGenero identidadgenero = new IdentidadGenero();
				model.addAttribute("identidadgenero", identidadgenero);
				return "identidadGeneros/crear_identidad";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/identidadgenero")
	public String saveIdentidadGenero(@ModelAttribute("identidadgenero") IdentidadGenero identidadgenero) {

		identidadGeneroService.saveIdentidadGenero(identidadgenero);
		return "redirect:/identidadgenero";
	}

	@GetMapping("/identidadgenero/edit/{id}")
	public String editIdentidadGenero(@PathVariable Integer id, Model model) {

		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("identidadgenero", identidadGeneroService.getIdentidadGeneroById(id));
				return "identidadGeneros/edit_identidadgenero";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	@PostMapping("/identidadgenero/{id}")
	public String updateUsuario(@PathVariable Integer id,@ModelAttribute("identidadgenero") IdentidadGenero identidadgenero, Model model) {
		IdentidadGenero existingIdentidadGenero= identidadGeneroService.getIdentidadGeneroById(id);
		existingIdentidadGenero.setId(id);
		existingIdentidadGenero.setCedula(identidadgenero.getCedula());
		existingIdentidadGenero.setGenero(identidadgenero.getGenero());
		existingIdentidadGenero.setCodigoPais(identidadgenero.getCodigoPais());

		identidadGeneroService.updateIdentidadGenero(identidadgenero);
		return "redirect:/identidadgenero";
	}
	@GetMapping("/identidadgenero/{Id}")
	public String deleteidentidadgenero(@PathVariable Integer Id) {

		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				//Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
				//bitacoraService.saveBitacora(bitacora);

				identidadGeneroService.deleteIdentidadGeneroById(Id);
				return "redirect:/identidadgenero";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
	}

}
