
package com.if7100.controller;

import org.springframework.stereotype.Controller;

import com.if7100.entity.Usuario;
import com.if7100.entity.Paises;
import com.if7100.entity.IdentidadGenero;
import com.if7100.service.GenerosService;
import com.if7100.service.IdentidadGeneroService;
import com.if7100.service.PaisesService;

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
	private PaisesService paisesService;
	private GenerosService generoService;

	public IdentidadGeneroController(IdentidadGeneroService identidadGeneroService, PaisesService paisesService,
			GenerosService generoService) {
		super();
		this.identidadGeneroService = identidadGeneroService;
		this.paisesService = paisesService;
		this.generoService = generoService;
	}

	@GetMapping("/identidadgenero")
	public String listStudents(Model model) {
		model.addAttribute("identidadgenero", identidadGeneroService.getAllIdentidadGenero());
		model.addAttribute("generos", generoService.obtencionGeneros(identidadGeneroService.getAllIdentidadGenero()));
		model.addAttribute("paises", paisesService.obtencionPaisesRelacionados(identidadGeneroService.getAllIdentidadGenero()));
		return "identidadgenero";
	}

	@GetMapping("/identidadgenero/new")
	public String createIdentidadGenero(Model model) {

		IdentidadGenero identidadgenero = new IdentidadGenero();
		Paises paises = new Paises();

		model.addAttribute("identidadgenero", identidadgenero);
		model.addAttribute("paises", paisesService.getAllPaises());
		model.addAttribute("generos", generoService.getAllGeneros());
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
		model.addAttribute("paises", paisesService.getAllPaises());
		model.addAttribute("generos", generoService.getAllGeneros());
		return "edit_identidadgenero";
	}

	@PostMapping("/identidadgenero/{id}")
	public String updateUsuario(@PathVariable Integer id,
			@ModelAttribute("identidadgenero") IdentidadGenero identidadgenero, Model model) {
		IdentidadGenero existingIdentidadGenero = identidadGeneroService.getIdentidadGeneroById(id);
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
