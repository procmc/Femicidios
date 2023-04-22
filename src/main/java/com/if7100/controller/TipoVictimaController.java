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
 * @author Liss
 * Fecha: 11 de abril del 2023
 */
import com.if7100.entity.TipoVictima;
import com.if7100.service.TipoVictimaService;
   

@Controller
public class TipoVictimaController {
	
private TipoVictimaService tipovictimaService;

 public TipoVictimaController(TipoVictimaService tipovictimaService) {
	 super();
	 this.tipovictimaService=tipovictimaService;
 }
 
 @GetMapping("/tipovictima")
 public String listStudents(Model model) {
	 model.addAttribute("tipoVictima",tipovictimaService.getAllTipoVictima());
	 return "tipo_victima";
 }
 
 // creacion de un nuevo usuario
 @GetMapping("/tipoVictima/new")
 public String createTipoVictimaForm(Model model) {
	 TipoVictima tipoV=new TipoVictima();
	 model.addAttribute("tipoVictima", tipoV);
	 return "create_tipo_victima";
 }
 
 @PostMapping("/tipovictima")
 public String saveUsuario(@ModelAttribute("tipoVictima") TipoVictima tipoVictima) {
	 tipovictimaService.saveTipoVictima(tipoVictima);
	 return "redirect:/tipovictima";
 }
 
 //Eliminar Usuario
 @GetMapping("/tipovictima/{Id}")
 public String deleteTipoVictima(@PathVariable Integer Id) {
	 tipovictimaService.deleteTipoVictimaById(Id);
	 return "redirect:/tipovictima";
 }
 
 //EditarUsuario
 @GetMapping("/tipovictima/edit/{Id}")
 public String editTipoVictimaForm(@PathVariable Integer Id, Model model) {
	 model.addAttribute("tipovictima", tipovictimaService.getTipoVictimaById(Id));
	 return "edit_tipo_victima";
 }
 
 @PostMapping("/tipovictima/{Id}")
 public String updateUsuario(@PathVariable Integer Id, @ModelAttribute("tipovictima") TipoVictima tipoVictima) {
	 TipoVictima existingTipoVictima=tipovictimaService.getTipoVictimaById(Id);
	 existingTipoVictima.setCI_Codigo(Id);
	 existingTipoVictima.setCVTitulo(tipoVictima.getCVTitulo());
	 existingTipoVictima.setCVDescripcion(tipoVictima.getCVDescripcion()); 
	 tipovictimaService.updateTipoVictima(existingTipoVictima);
	 return "redirect:/tipovictima";
 }
}
