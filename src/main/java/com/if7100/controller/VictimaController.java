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
 * @author Hadji
 *
 */


import com.if7100.service.VictimaService;
import com.if7100.entity.Victima;

@Controller
public class VictimaController {
	
	private VictimaService victimaService;
	
	public VictimaController (VictimaService victimaService)
	{
		super();
		this.victimaService=victimaService;
	}

	@GetMapping("/victima")
	public String listStudents(Model model) {
		model.addAttribute("victima", victimaService.getAllVictima());
		
		return "victima";
	}
	
	@GetMapping("/victima/new")
	public String createVictimaForm (Model model) {
		
		Victima victima = new Victima();
		model.addAttribute("victima", victima);
		return "create_victima";
	}
	
	
	@PostMapping("/victima")
	public String saveVictima (@ModelAttribute("victima") Victima victima) {
		
		victimaService.saveVictima(victima);
		return "redirect:/victima";
	}
	
	@GetMapping("/victima/{Id}")
	public String deleteVictima (@PathVariable Integer Id) {
		
		victimaService.deleteVictimaById(Id);
		return "redirect:/victima";
	}
	
	
	@GetMapping("/victima/edit/{id}")
	public String editVictimaForm (@PathVariable Integer id, Model model) {
		
		model.addAttribute("victima", victimaService.getVictimaById(id));
		return "edit_victima";
		
	}
	
	
	@PostMapping("/victima/{id}")
	public String updateVictima (@PathVariable Integer id, 
								 @ModelAttribute("victima") Victima victima,
								 Model model) {
		
		Victima existingVictima = victimaService.getVictimaById(id);
		existingVictima.setCI_Id(id);
		existingVictima.setCVDNI(victima.getCVDNI());
		existingVictima.setCVNombre(victima.getCVNombre());
		existingVictima.setCVApellidoPaterno(victima.getCVApellidoPaterno());
		existingVictima.setCVApellidoMaterno(victima.getCVApellidoMaterno());
		existingVictima.setCVEdad(victima.getCVEdad());
		existingVictima.setCVGenero(victima.getCVGenero());
		existingVictima.setCVLugarNac(victima.getCVLugarNac());
		existingVictima.setCVOrientaSex(victima.getCVOrientaSex());
		
		victimaService.updateVictima(existingVictima);
		return "redirect:/victima";
		
	}
	
}
