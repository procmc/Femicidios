package com.if7100.controller;

import com.if7100.entity.OrientacionSexual;
import com.if7100.service.OrientacionSexualService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrientacionSexualController {
	
 private OrientacionSexualService orientacionService;
 
 public OrientacionSexualController(OrientacionSexualService orientacionService) {
	 super();
	 this.orientacionService=orientacionService;
 }
 
 @GetMapping("/orientacionessexuales")
 public String listOrientacionesSexuales(Model model) {
	 
	 model.addAttribute("orientacionesSexuales",orientacionService.getAllOrientacionesSexuales());
	 return "orientacionesSexuales";
 }
 
 @GetMapping("/orientacionessexuales/new")
 public String createOrientacionSexualForm(Model model) {
	 model.addAttribute("orientacionSexual",new OrientacionSexual());
	 return "create_orientacionSexual";
 }
 
 @PostMapping("/orientacionessexuales")
 public String saveOrientacion(@ModelAttribute("orientacionSexual") OrientacionSexual orientacion) {
	 orientacionService.saveOrientacionSexual(orientacion);
	 return "redirect:/orientacionessexuales";
 }
 
 @GetMapping("/orientacionessexuales/{id}")
 public String deleteOrientacionSexual(@PathVariable int id) {
	 
	 orientacionService.deleteOrientacionSexualByCodigo(id);
	 return "redirect:/orientacionessexuales";
 }
 
 @GetMapping("/orientacionessexuales/edit/{id}")
 public String editOrientacionForm(Model model,@PathVariable int id) {
	 
	 model.addAttribute("orientacionSexual", orientacionService.getOrientacionSexualByCodigo(id));
	 return "edit_orientacionSexual";
 }
 
 @PostMapping("/orientacionessexuales/{id}")
 public String updateOrientacionSexual(@PathVariable int id, @ModelAttribute("orientacion") OrientacionSexual orientacion, Model model) {
	 OrientacionSexual existingOrientacion=orientacionService.getOrientacionSexualByCodigo(id);
	 existingOrientacion.setCI_Codigo(id);
	 existingOrientacion.setCVTitulo(orientacion.getCVTitulo());
	 existingOrientacion.setCVDescripcion(orientacion.getCVDescripcion());
	 
	 orientacionService.updateOrientacionSexual(existingOrientacion);
	 return "redirect:/orientacionessexuales";
 }
 
 
 
 
}
