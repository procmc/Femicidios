package com.if7100.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.if7100.service.VictimaService;

import com.if7100.entity.Victima;


@Controller
public class VictimaController {
	
	private  VictimaService victimaService;

	public VictimaController(VictimaService victimaService) {
		super();
		this.victimaService = victimaService;
	}
	
	@GetMapping("/victima")
	public String listStudents(Model model) {
		model.addAttribute("victima", victimaService.getAllVictima());
		return "victima";
	}
	@GetMapping("/create_victima")
	public String createUsuarioForm(Model model) {
		Victima victima=new Victima();
		model.addAttribute("victima", victima);
		return "victima";
	}
	
	@GetMapping("/victima/{Id}")
	public String deleteUsuario(@PathVariable Integer Id) {
		victimaService.deleteVictimaById(Id);
		return "edirect:/usuarios";
	}
	
	@PostMapping("/victima")
	public String saveVictima(@ModelAttribute("victima") Victima victima) {
		
		victimaService.saveVictima(victima);
		return "redirect:/victima";
	}

	public VictimaService getVictimaService() {
		return victimaService;
	}



	
	
	
}
