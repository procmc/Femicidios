package com.if7100.controller;
import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Hecho;
import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.NivelEducativoService;
import com.if7100.service.PerfilService;


@Controller
public class NivelEducativoController {
private NivelEducativoService nivelEducativoService;
//instancias para control de acceso
private UsuarioRepository usuarioRepository;
private Perfil perfil;
private PerfilService perfilService;
//instancias para control de bitacora
private BitacoraService bitacoraService;
private Usuario usuario;

	public NivelEducativoController(BitacoraService bitacoraService,
NivelEducativoService nivelEducativoService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	
		super();
		this.nivelEducativoService = nivelEducativoService;
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

@GetMapping("/nivelEducativo")
	public String listStudents(Model model) {
		model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
		return "nivelEducativo/nivelEducativo";
}
@GetMapping("/nivelEducativo/new")
public String createUsuarioForm(Model model){
	
	try {
		this.validarPerfil();
		if(!this.perfil.getCVRol().equals("Consulta")) {
			
			NivelEducativo nivelEducativo = new NivelEducativo();
			model.addAttribute("nivelEducativo", nivelEducativo);
			return"nivelEducativo/create_nivelEducativo";
		}else {
			return "SinAcceso";
		}
		
	}catch (Exception e) {
		return "SinAcceso";
	}
}

@PostMapping("/nivelEducativo")
public String saveNivelEducativo(@ModelAttribute("nivelEducativo") NivelEducativo nivelEducativo) {
	nivelEducativoService.saveNivelEducativo(nivelEducativo);
	return "redirect:/nivelEducativo";
}

@GetMapping("/nivelEducativo/{id}")
public String deleteNivelEducativo(@PathVariable Integer id) {
	
	try {
		this.validarPerfil();
		if(!this.perfil.getCVRol().equals("Consulta")) {
			
			String descripcion = "Elimino un nivel educativo";
			Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
			bitacoraService.saveBitacora(bitacora);
			
			nivelEducativoService.deleteNivelEducativoById(id);
			return "redirect:/nivelEducativo";
		}else {
			return "SinAcceso";
		}
		
	}catch (Exception e) {
		return "SinAcceso";
	}
}
@GetMapping("/nivelEducativo/edit/{id}")
public String editNivelEducativoForm(@PathVariable Integer id, Model model) {
	
	try {
		this.validarPerfil();
		if(!this.perfil.getCVRol().equals("Consulta")) {
			
			model.addAttribute("nivelEducativo", nivelEducativoService.getNivelEducativoById(id));
			return "nivelEducativo/edit_nivelEduactivo";
		}else {
			return "SinAcceso";
		}
		
	}catch (Exception e) {
		return "SinAcceso";
	}
}
@PostMapping("/nivelEducativo/{id}")
public String updateNivelEducativo (@PathVariable Integer id, @ModelAttribute ("nivelEducativo")NivelEducativo nivelEducativo, Model model) {
	NivelEducativo existingNivelEducativo = nivelEducativoService.getNivelEducativoById(id);
	existingNivelEducativo.setCI_Id(id);
	existingNivelEducativo.setCVTitulo(nivelEducativo.getCVTitulo());
	existingNivelEducativo.setCVDescripcion(nivelEducativo.getCVDescripcion());
	
	
	nivelEducativoService.updateNivelEducativo(existingNivelEducativo);
	return "redirect:/nivelEducativo";
}


}
