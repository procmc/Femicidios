/**
 * 
 */
package com.if7100.controller;
import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.service.BitacoraService;

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

import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;

import java.util.List;
import java.util.stream.IntStream;


@Controller
public class RegistroPerfilController {

	private PerfilService perfilService;
	private UsuarioPerfilService usuarioPerfilService;
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


	public RegistroPerfilController(BitacoraService bitacoraService,
PerfilService perfilService, UsuarioRepository usuarioRepository, UsuarioPerfilService usuarioPerfilService,
UsuarioPerfilRepository usuarioPerfilRepository) {
		super();
		this.perfilService = perfilService;
		this.usuarioRepository = usuarioRepository;
		this.bitacoraService= bitacoraService;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;

	}
	
	 private void validarPerfil() {
	    	
			try {
				 Usuario usuario=new Usuario();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			    String username = authentication.getName();
			   
			    this.usuario= new Usuario(usuarioRepository.findByCVCedula(username));

				List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilRepository.findByUsuario(this.usuario);

				this.perfil = new Perfil(
						perfilService.getPerfilById(usuarioPerfiles.get(0).getPerfil().getCI_Id()));
					
			}catch (Exception e) {
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
	
	@GetMapping("/perfiles")
	public String listPerfiles(Model model) {
		this.validarPerfil();
		return "redirect:/perfil/1";
	}

	@GetMapping("/perfil/{pg}")
	public String listPerfil(Model model, @PathVariable Integer pg){

		try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)) {

				if (pg < 1){
					return "redirect:/perfil/1";
				}

				int numeroTotalElementos = perfilService.getAllPerfiles().size();

				Pageable pageable = initPages(pg, 5, numeroTotalElementos);

				Page<Perfil> perfilPage = perfilService.getAllPerfilesPage(pageable);

				List<Integer> nPaginas = IntStream.rangeClosed(1, perfilPage.getTotalPages())
						.boxed()
						.toList();
				model.addAttribute("PaginaActual", pg);
				model.addAttribute("nPaginas", nPaginas);
				model.addAttribute("perfiles", perfilPage.getContent());
				return "perfiles/perfiles";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}

	}

	@GetMapping("/registroPerfil")
	public String createPerfilForm (Model model) {
		
		try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)) {
				
				Perfil perfil = new Perfil();
				model.addAttribute("perfil", perfil);
				return "perfiles/registroPerfil";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/registroPerfil")
	public String savePerfil (@ModelAttribute Perfil perfil) {
		this.validarPerfil();
		perfilService.savePerfil(perfil);
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en registro de perfil", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/perfiles";
	}
	
	@GetMapping("/perfiles/{id}")
	public String deletePerfil (@PathVariable Integer id) {
		
		try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)) {
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en registro de perfil", this.usuario.getOrganizacion().getCodigoPais()));

				perfilService.deletePerfilById(id);
				return "redirect:/perfiles";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@GetMapping("/perfiles/edit/{id}")
	public String editUsuarioForm (@PathVariable Integer id, Model model) {
		
		try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)) {
				
				model.addAttribute("perfil", perfilService.getPerfilById(id));
				return "perfiles/edit_perfil";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/perfiles/{id}")
	public String updateUsuario (@PathVariable Integer id, @ModelAttribute Perfil perfil) {
		this.validarPerfil();
		Perfil existingUsuario = perfilService.getPerfilById(id);
		existingUsuario.setCI_Id(id);
		existingUsuario.setCVDescripcion(perfil.getCVDescripcion());
		existingUsuario.setCVRol(perfil.getCVRol());
		
		perfilService.updatePerfil(existingUsuario);
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en registro de perfil", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/perfiles";
	}
}
