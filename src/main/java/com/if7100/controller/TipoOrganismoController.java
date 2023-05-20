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
 * @author Adam Smasher
 */
import com.if7100.service.TipoOrganismoService;
import com.if7100.entity.TipoOrganismo;

@Controller
public class TipoOrganismoController {
private TipoOrganismoService tipoOrganismoService;
	
	public TipoOrganismoController(TipoOrganismoService tipoOrganismoService) {
	super();
	this.tipoOrganismoService= tipoOrganismoService;
}
	@GetMapping("/tipoOrganismo")
	public String listStudents(Model model) {
		model.addAttribute("tipoOrganismo", tipoOrganismoService.getAllTipoOrganismos());
		return "tipoOrganismo/tipoOrganismo";
	}

	@GetMapping("/tipoOrganismo/new")
	public String CreateTipoOrganismoForm (Model model) {
		TipoOrganismo tipoOrganismo= new TipoOrganismo();
		model.addAttribute("tipoOrganismo", tipoOrganismo);
		return "tipoOrganismo/create_tipoOrganismo";	
	}
	
	@PostMapping("/tipoOrganismo")
	public String saveTipoOrganismo (@ModelAttribute("tipoOrganismo") TipoOrganismo tipoOrganismo) {
	    tipoOrganismoService.saveTipoOrganismo(tipoOrganismo);
		return "redirect:/tipoOrganismo";	
	}
	

	@GetMapping("/tipoOrganismo/{Codigo}")
	public String deleteTipoOrganismoByCodigo (@PathVariable Integer Codigo) {
		tipoOrganismoService.deleteTipoOrganismoByCodigo(Codigo);
		return "redirect:/tipoOrganismo";	
	}
	

	@GetMapping("/tipoOrganismo/edit/{Codigo}")
	public String editTipoOrganismoForm (@PathVariable Integer Codigo,Model model) {
		model.addAttribute("tipoOrganismo", tipoOrganismoService.getTipoOrganismoByCodigo(Codigo));
		return "tipoOrganismo/edit_tipoOrganismo";	
	}
	
	@PostMapping("/tipoOrganismo/{Codigo}")
	public String updateOrganismoLugar (@PathVariable Integer Codigo ,@ModelAttribute("tipoOrganismo") TipoOrganismo tipoOrganismo, Model model) {
	   
		TipoOrganismo existingTipoOrganismo = tipoOrganismoService.getTipoOrganismoByCodigo(Codigo);
	    existingTipoOrganismo.setCI_Codigo(Codigo);
	    existingTipoOrganismo.setCVTitulo(tipoOrganismo.getCVTitulo());
	    existingTipoOrganismo.setCVDescripcion(tipoOrganismo.getCVDescripcion());
		tipoOrganismoService.updateTipoOrganismo(existingTipoOrganismo);
		return "redirect:/tipoOrganismo";	
	}
	
	
}