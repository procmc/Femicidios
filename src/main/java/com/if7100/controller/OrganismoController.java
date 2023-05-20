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
import com.if7100.service.TipoOrganismoService;

@Controller
public class OrganismoController {
	
 private OrganismoService organismoService;
 
 private TipoOrganismoService tipoOrganismoService;
 
 public OrganismoController (OrganismoService organismoService, TipoOrganismoService tipoOrganismoService) {
	 super();
	 this.organismoService=organismoService;
	 this.tipoOrganismoService=tipoOrganismoService;
 }
 
 @GetMapping("/organismos")
 public String listOrganismos(Model model) {
	 
	 model.addAttribute("organismos",organismoService.getAllOrganismos());
	 return "organismos/organismos";
 }
 
 @GetMapping("/organismos/new")
 public String createOrganismoForm(Model model) {
	 model.addAttribute("organismo",new Organismo());
	 model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
	 return "organismos/create_organismo";
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
	 model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
	 return "organismos/edit_organismo";
 }
 
 @PostMapping("/organismos/{id}")
 public String updateOrganismo(@PathVariable int id, @ModelAttribute("organismo") Organismo organismo, Model model) {
	 Organismo existingOrganismo=organismoService.getOrganismoById(id);
	 model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());

	 existingOrganismo.setCI_Id(id);
	 existingOrganismo.setCVNombre(organismo.getCVNombre());
	 existingOrganismo.setCVRol(organismo.getCVRol());
	 existingOrganismo.setCVTipo_Organismo(organismo.getCVTipo_Organismo());
	 existingOrganismo.setCVNacionalidad(organismo.getCVNacionalidad());
	 existingOrganismo.setCVContacto(organismo.getCVContacto());

	 
	 organismoService.updateOrganismo(existingOrganismo);
	
	 return "redirect:/organismos";
 }
 
 
 
 
}
