/**
 * 
 */
package com.if7100.controller;

import org.springframework.stereotype.Controller;

import com.if7100.entity.Usuario;
import com.if7100.entity.IdentidadGenero;
import com.if7100.service.IdentidadGeneroService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	public IdentidadGeneroController(IdentidadGeneroService identidadGeneroService) {
		super();
		this.identidadGeneroService = identidadGeneroService;
	}

	@GetMapping("/identidadgenero")
	public String listStudents(Model model) {
		model.addAttribute("identidadgenero", identidadGeneroService.getAllIdentidadGenero());
		return "identidadgenero";
	}

	@GetMapping("/identidadgenero/new")
	public String createIdentidadGenero(Model model) {

		IdentidadGenero identidadgenero = new IdentidadGenero();
		model.addAttribute("identidadgenero", identidadgenero);
		return "crear_identidad";
	}

	@PostMapping("/identidadgenero")
	public String saveIdentidadGenero(@ModelAttribute("identidadgenero") IdentidadGenero identidadgenero) {

		identidadGeneroService.saveIdentidadGenero(identidadgenero);
		return "redirect:/identidadgenero";
	}

	@GetMapping("/identidadgenero/edit/{id}")
	public String editIdentidadGenero(@PathVariable Integer id, Model model) {
		model.addAttribute("identidadgenero", identidadGeneroService.getIdentidadGeneroById(id));
		return "edit_identidadgenero";
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
		identidadGeneroService.deleteIdentidadGeneroById(Id);
		return "redirect:/identidadgenero";
	}
	
}
