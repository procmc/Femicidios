package com.if7100.controller;

import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Organismo;
import com.if7100.service.OrganismoService;

@Controller
public class OrganismoController {
	
 private OrganismoService organismoService;
 
 public OrganismoController (OrganismoService organismoService) {
	 super();
	 this.organismoService=organismoService;
 }
 
 @GetMapping("/organismos")
 public String listStudents(Model model) {
	 
	 model.addAttribute("organismos",organismoService.getAllOrganismos());
	 return "organismos";
 }
 
 @GetMapping("/organismos/new")
 public String createOrganismoForm(Model model) {
	 model.addAttribute("organismo",new Organismo());
	 return "create_organismo";
 }
 
 @PostMapping("/organismos")
 public String savOrganismo(@ModelAttribute("organismo") Organismo organismo) {
	 
	 if (!organismo.getCVNombre().equals("") && !organismo.getCVRol().equals("") && 
	 !organismo.getCVNacionalidad().equals("") && !organismo.getCVContacto().equals("") &&
	 !organismo.getCVTipo_Organismo().equals("")){
		 organismoService.saveOrganismo(organismo);
		 return "redirect:/organismos";
	}
	 
	 return "redirect:/organismos";
	 
 }
 
 @GetMapping("/organismos/{id}")
 public String deleteOrganismo(@PathVariable int id) {
	 
	 organismoService.deleteOrganismoById(id);
	 return "redirect:/organismos";
 }
 
 @GetMapping("/organismos/edit/{id}")
 public String editOrganismoForm(Model model,@PathVariable int id) {
	 model.addAttribute("organismo", organismoService.getOrganismoById(id));
	 return "edit_organismo";
 }
 
 @PostMapping("/organismos/{id}")
 public String updateOrganismo(@PathVariable int id, @ModelAttribute("organismo") Organismo organismo, Model model) {
	 Organismo existingOrganismo=organismoService.getOrganismoById(id);
	 if (!organismo.getCVNombre().equals("") && !organismo.getCVRol().equals("") && 
			 !organismo.getCVNacionalidad().equals("") && !organismo.getCVContacto().equals("")&&
			 !organismo.getCVTipo_Organismo().equals("")){
	 existingOrganismo.setCI_Id(id);
	 existingOrganismo.setCVNombre(organismo.getCVNombre());
	 existingOrganismo.setCVRol(organismo.getCVRol());
	 existingOrganismo.setCVTipo_Organismo(organismo.getCVTipo_Organismo());
	 existingOrganismo.setCVNacionalidad(organismo.getCVNacionalidad());
	 existingOrganismo.setCVContacto(organismo.getCVContacto());

	 
	 organismoService.updateOrganismo(existingOrganismo);
	 return "redirect:/organismos";
	 }
	 return "redirect:/organismos";
 }
 
 
 
 
}
