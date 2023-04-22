/**
 * 
 */
package com.if7100.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.if7100.entity.Modalidad;
import com.if7100.service.ModalidadService;

/**
 * @author tisha
 *Fecha 19 de abril 2023
 */


@Controller
public class ModalidadController {

	private ModalidadService modalidadService;

	public ModalidadController(ModalidadService modalidadService) {
		super();
		this.modalidadService = modalidadService;
	}
	
	@GetMapping("/modalidad")
	public String listStudents(Model model) {
		model.addAttribute("modalidad",modalidadService.getAllModalidad());
		return "modalidad";
	}
	
	@GetMapping("/modalidad/new")
	public String createModalidadForm(Model model) {
		Modalidad modalidad=new Modalidad();
		model.addAttribute("modalidad",modalidad);
		return "create_modalidad";
	}
	
	@PostMapping("/modalidad")
	public String saveModalidad(@ModelAttribute("modalidad") Modalidad modalidad) {
		
		modalidadService.saveModalidad(modalidad);
		return "redirect:/modalidad";
	}
	
	@GetMapping("/modalidad/{codigo}")
	public String deleteModalidad (@PathVariable Integer codigo) {
		modalidadService.deleteModalidadById(codigo);
		return "redirect:/modalidad";
	}
	
	@GetMapping("/modalidad/edit/{codigo}")
	public String editModalidadForm(@PathVariable Integer codigo, 
			                      Model model) {
		model.addAttribute("modalidad",modalidadService.getModalidadById(codigo));
		return "edit_modalidad";
	}
	
	@PostMapping("/modalidad/{codigo}")
	public String updateModalidad(@PathVariable Integer codigo, 
			                    @ModelAttribute ("modalidad") Modalidad modalidad, 
			                    Model model) {
		Modalidad existingModalidad= modalidadService.getModalidadById(codigo);
		existingModalidad.setCI_Codigo(codigo);
		existingModalidad.setCVTitulo(modalidad.getCVTitulo());
		existingModalidad.setCVDescripcion(modalidad.getCVDescripcion());
		modalidadService.saveModalidad(existingModalidad);
		return "redirect:/modalidad";
	}
	
	
}
