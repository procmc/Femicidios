package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.OrientacionSexual;
import com.if7100.service.OrientacionSexualService;

@Controller
public class OrientacionSexualController {
	
 private OrientacionSexualService orientacionService;
 
 public OrientacionSexualController (OrientacionSexualService orientacionService) {
	 super();
	 this.orientacionService=orientacionService;
 }
 
 @GetMapping("/orientacionesSexuales")
 public String listStudents(Model model) {
	 
	 model.addAttribute("orientacionesSexuales",orientacionService.getAllOrientacionesSexuales());
	 return "orientacionessexuales/orientacionesSexuales";
 }
 
 @GetMapping("/orientacionesSexuales/new")
 public String createUsuarioForm(Model model) {
	 model.addAttribute("orientacion",new OrientacionSexual());
	 return "orientacionessexuales/create_orientacionesSexuales";
 }
 
 @PostMapping("/orientacionesSexuales")
 public String saveOrientacion(@ModelAttribute("orientacion") OrientacionSexual orientacion) {
	 orientacionService.saveOrientacionSexual(orientacion);
	 return "redirect:/orientacionesSexuales";
 }
 
 @GetMapping("/orientacionesSexuales/{id}")
 public String deleteOrientacion(@PathVariable int id) {
	 
	 orientacionService.deleteOrientacionSexualByCodigo(id);
	 return "redirect:/orientacionesSexuales";
 }
 
 @GetMapping("/orientacionesSexuales/edit/{id}")
 public String editOrientacionForm(Model model,@PathVariable int id) {
	 
	 model.addAttribute("orientacion", orientacionService.getOrientacionSexualByCodigo(id));
	 return "orientacionessexuales/edit_orientacionesSexuales";
 }
 
 @PostMapping("/orientacionesSexuales/{id}")
 public String updateOrientacionSexual(@PathVariable int id, @ModelAttribute("orientacion") OrientacionSexual orientacion, Model model) {
	 OrientacionSexual existingOrientacion=orientacionService.getOrientacionSexualByCodigo(id);
	 existingOrientacion.setCI_Codigo(id);
	 existingOrientacion.setCVTitulo(orientacion.getCVTitulo());
	 existingOrientacion.setCVDescripcion(orientacion.getCVDescripcion());
	 
	 orientacionService.updateOrientacionSexual(existingOrientacion);
	 return "redirect:/orientacionesSexuales";
 }
 
 
 
 
}
