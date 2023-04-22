package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.ProcesoJudicial;
import com.if7100.service.ProcesoJudicialService;

@Controller
public class ProcesoJudicialController {
	
 private ProcesoJudicialService procesoJudicialService;
 
 public ProcesoJudicialController (ProcesoJudicialService procesoJudicialService) {
	 super();
	 this.procesoJudicialService=procesoJudicialService;
 }
 
 @GetMapping("/procesosJudiciales")
 public String listStudents(Model model) {
	 
	 model.addAttribute("procesosJudiciales",procesoJudicialService.getAllProcesosJudiciales());
	 return "procesosJudiciales";
 }
 
 @GetMapping("/procesosJudiciales/new")
 public String createProcesoJudicialForm(Model model) {
	 model.addAttribute("procesoJudicial",new ProcesoJudicial());
	 return "create_procesosJudiciales";
 }
 
 @PostMapping("/procesosJudiciales")
 public String saveProcesoJudicial(@ModelAttribute("procesoJudicial") ProcesoJudicial procesoJudicial) {
	 procesoJudicialService.saveProcesoJudicial(procesoJudicial);
	 return "redirect:/procesosJudiciales";
 }
 
 @GetMapping("/procesosJudiciales/{id}")
 public String deleteProcesoJudicial(@PathVariable int id) {
	 
	 procesoJudicialService.deleteProcesoJudicialById(id);
	 return "redirect:/procesosJudiciales";
 }
 
 @GetMapping("/procesosJudiciales/edit/{id}")
 public String editProcesoJudicialForm(Model model,@PathVariable int id) {
	 
	 model.addAttribute("procesoJudicial", procesoJudicialService.getProcesoJudicialById(id));
	 return "edit_procesosJudiciales";
 }
 
 @PostMapping("/procesosJudiciales/{id}")
 public String updateProcesoJudicial(@PathVariable int id, @ModelAttribute("procesoJudicial") ProcesoJudicial procesoJudicial, Model model) {
	 ProcesoJudicial existingProcesoJudicial=procesoJudicialService.getProcesoJudicialById(id);
	 existingProcesoJudicial.setCI_IdProceso(id);
	 existingProcesoJudicial.setCVEstado(procesoJudicial.getCVEstado());
	 existingProcesoJudicial.setCIDenunciante(procesoJudicial.getCIDenunciante());
	 //existingProcesoJudicial.setCDFechaApertura(procesoJudicial.getCDFechaApertura());
	 existingProcesoJudicial.setCIPersonasImputadas(procesoJudicial.getCIPersonasImputadas());
	 existingProcesoJudicial.setCVPartes(procesoJudicial.getCVPartes());
	 
	 procesoJudicialService.updateProcesoJudicial(existingProcesoJudicial);
	 return "redirect:/procesosJudiciales";
 }
 
 
 
 
}
