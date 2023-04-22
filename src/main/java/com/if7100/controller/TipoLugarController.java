/**
 * 
 */
package com.if7100.controller;

import org.springframework.stereotype.Controller;//para el controlador
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author kendall B
 * Fecha: 11 de abril del 2023
 */
import com.if7100.service.TipoLugarService;
import com.if7100.entity.TipoLugar;

@Controller
public class TipoLugarController {

	private TipoLugarService tipoLugarService;
	
	public TipoLugarController(TipoLugarService tipoLugarService) {
	super();
	this.tipoLugarService= tipoLugarService;
}
	//consultar
	@GetMapping("/tipoLugar")//muestra el listado de usuarios
	public String listStudents(Model model) {
		model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
		return "tipoLugar";
	}
	//agregar
	@GetMapping("/tipoLugar/new")// envia el modelo a la pagina de crear usuario
	public String CreateTipoLugarForm (Model model) {
		TipoLugar tipoLugar= new TipoLugar();
		model.addAttribute("tipoLugar", tipoLugar);
		return "create_tipoLugar";	
	}
	
	@PostMapping("/tipoLugar")// guarda el usuario y lo devuelve a la pagina usuarios con los datos nuevos
	public String saveTipoLugar (@ModelAttribute("tipoLugar") TipoLugar tipoLugar) {
	    tipoLugarService.saveTipoLugar(tipoLugar);
		return "redirect:/tipoLugar";	
	}
	
	//eliminar
	@GetMapping("/tipoLugar/{Id}")// envia el modelo a la pagina de crear usuario
	public String deleteTipoLugarByCodigo (@PathVariable Integer Codigo) {
		tipoLugarService.deleteTipoLugarByCodigo(Codigo);
		return "redirect:/tipoLugar";	
	}
	
	//modificar
	@GetMapping("/tipoLugar/edit/{id}")// envia el modelo a la pagina de editar usuario
	public String editTipoLugarForm (@PathVariable Integer Codigo,Model model) {
		model.addAttribute("tipoLugar", tipoLugarService.getTipoLugarByCodigo(Codigo));
		return "edit_tipoLugar";	
	}
	
	@PostMapping("/tipoLugar/{id}")// guarda el cambio y lo devuelve a la pagina usuarios con los datos nuevos
	public String updateTipoLugar (@PathVariable Integer Codigo ,@ModelAttribute("tipoLugar") TipoLugar tipoLugar, Model model) {
	   
		TipoLugar existingTipoLugar = tipoLugarService.getTipoLugarByCodigo(Codigo);
	    existingTipoLugar.setCI_Codigo(Codigo);
	    existingTipoLugar.setCVTitulo(tipoLugar.getCVTitulo());
	    existingTipoLugar.setCVDescripcion(tipoLugar.getCVDescripcion());
		tipoLugarService.updateTipoLugar(existingTipoLugar);
		return "redirect:/tipoLugar";	
	}
	
	
}