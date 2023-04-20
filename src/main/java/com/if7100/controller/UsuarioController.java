/**
 * 
 */
package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Carlos Morales Castro
 * Fecha: 1 abril 2023 
 */

import com.if7100.entity.Usuario;
import com.if7100.service.UsuarioService;

@Controller
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	public UsuarioController (UsuarioService usuarioService)
	{
		super();
		this.usuarioService=usuarioService;
	}

	@GetMapping("/usuarios")
	public String listStudents(Model model) {
		model.addAttribute("usuarios", usuarioService.getAllUsuarios());
		return "usuarios";
	}
	
	@GetMapping("/usuarios/new")
	public String createUsuarioForm (Model model) {
		
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "create_usuario";
	}
	
	
	@PostMapping("/usuarios")
	public String saveUsuario (@ModelAttribute("usuario") Usuario usuario) {
		
		usuarioService.saveUsuario(usuario);
		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuarios/{Id}")
	public String deleteUsuario (@PathVariable Integer Id) {
		
		usuarioService.deleteUsuarioById(Id);
		return "redirect:/usuarios";
	}
	
	
	@GetMapping("/usuarios/edit/{id}")
	public String editUsuarioForm (@PathVariable Integer id, Model model) {
		
		model.addAttribute("usuario", usuarioService.getUsuarioById(id));
		return "edit_usuario";
		
	}
	
	
	@PostMapping("/usuarios/{id}")
	public String updateUsuario (@PathVariable Integer id, 
								 @ModelAttribute("usuario") Usuario usuario,
								 Model model) {
		
		Usuario existingUsuario = usuarioService.getUsuarioById(id);
		existingUsuario.setCI_Id(id);
		existingUsuario.setCVNombre(usuario.getCVNombre());
		existingUsuario.setCVApellidos(usuario.getCVApellidos());
		
		
		usuarioService.updateUsuario(existingUsuario);
		return "redirect:/usuarios";
		
	}
	
}
