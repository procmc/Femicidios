/**
 * 
 */
package com.if7100.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import com.if7100.entity.Lugar;
import com.if7100.service.LugarService;
import com.if7100.service.TipoLugarService;
   

@Controller
public class LugarController {
	
	@Autowired
private LugarService lugarService;
private TipoLugarService tipoLugarService;


 public LugarController(LugarService lugarService, TipoLugarService tipoLugarService) {
	 super();
	 this.lugarService=lugarService;
	 this.tipoLugarService= tipoLugarService;
 }
 
 
 
 
 @GetMapping("/lugar")
 public String listStudents(Model model, @Param("palabraClave") String palabraClave) {
	 List<Lugar> listaLugar=lugarService.getAllLugares(palabraClave);
	 model.addAttribute("lugar",listaLugar);
	 model.addAttribute("palabraClave",palabraClave);
	 return "lugar";
 }
 
 
 
 
 
 
 
 // creacion de un nuevo usuario
 @GetMapping("/lugar/new")
 public String createLugarForm(Model model) {
	 Lugar lugar=new Lugar();
	 model.addAttribute("lugar", lugar);
	 
	 model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
	 
	 return "create_lugar";
 }
 
 @PostMapping("/lugar")
 public String saveLugar(@ModelAttribute("lugar") Lugar lugar) {
	 lugarService.saveLugar(lugar);
	 return "redirect:/lugar";
 }
 
 
 //Eliminar Usuario
 @GetMapping("/lugar/{Id}")
 public String deleteLugar(@PathVariable Integer Id) {
	 lugarService.deleteLugarById(Id);
	 return "redirect:/lugar";
 }
 
 
 //EditarUsuario
 @GetMapping("/lugar/edit/{Id}")
 public String editLugarForm(@PathVariable Integer Id, Model model) {
	 model.addAttribute("lugar", lugarService.getLugarById(Id));
	 model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
	 return "edit_lugar";
 }
 
 @PostMapping("/lugar/{Id}")
 public String updateLugar(@PathVariable Integer Id, @ModelAttribute("lugar") Lugar lugar) {
	 Lugar existingLugar=lugarService.getLugarById(Id);
	 existingLugar.setCI_Codigo(Id);
	 existingLugar.setCIF_DNI_Victima(lugar.getCIF_DNI_Victima());
	 existingLugar.setCV_Descripcion(lugar.getCV_Descripcion());
	 existingLugar.setCIF_Tipo_Lugar(lugar.getCIF_Tipo_Lugar());
	 existingLugar.setCV_Direccion(lugar.getCV_Direccion());
	 existingLugar.setCV_Ciudad(lugar.getCV_Ciudad());
	 existingLugar.setCV_Pais(lugar.getCV_Pais());
	 lugarService.updateLugar(existingLugar);
	 return "redirect:/lugar";
 }
}
