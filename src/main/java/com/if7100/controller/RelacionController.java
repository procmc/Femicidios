package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Relacion;
import com.if7100.service.RelacionService;

@Controller
public class RelacionController {

	private RelacionService relacionService;

	public RelacionController(RelacionService relacionService) {
		super();
		this.relacionService = relacionService;
	}
	
	@GetMapping("/tiporelaciones")
	public String listStudents(Model model) {
		model.addAttribute("relacion", relacionService.getAllTypeRelaciones());
		return "type_relaciones";
	}
	

	@GetMapping("/tiporelaciones/new")
	public String createRelacionForm(Model model) {
		Relacion relacion = new Relacion();
		model.addAttribute("relacion", relacion);
		return "create_relacion";// La página html
	}
	
	@PostMapping("/tiporelaciones")
	public String saveRelacion(@ModelAttribute("relacion") Relacion relacion) {
		relacionService.saveRelacion(relacion);
		return "redirect:/tiporelaciones";
	}
	
	
	@GetMapping("/tiporelaciones/{Id}")
	public String deleteRelacion(@PathVariable Integer Id) {
		Relacion relacion = new Relacion();
		relacionService.deleteRelacionById(Id);
		return "redirect:/tiporelaciones";
	}
	
	@GetMapping("/tiporelaciones/edit/{Id}")
	public String editRelacionForm(@PathVariable Integer Id, Model model) {
		model.addAttribute("relacion", relacionService.getRelacionById(Id));
		
		return "edit_relacion";
	}
	
	@PostMapping("/tiporelaciones/{Id}")
	public String updateRelacion(@PathVariable Integer Id, @ModelAttribute("relacion") Relacion relacion, Model model) {
		
		Relacion existingRelacion = relacionService.getRelacionById(Id);
		existingRelacion.setCI_codigo(Id);
		existingRelacion.setCVtitulo(relacion.getCVtitulo());
		existingRelacion.setCVdescripcion(relacion.getCVdescripcion());
	
		relacionService.updateRelacion(existingRelacion);
		return "redirect:/tiporelaciones";
	}
	
}
