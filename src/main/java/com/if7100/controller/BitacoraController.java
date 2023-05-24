/**
 * 
 */
package com.if7100.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.if7100.entity.Bitacora;
import com.if7100.service.BitacoraService;

/**
 * @author tisha
 *
 */


@Controller
public class BitacoraController {

	private BitacoraService bitacoraService;

	public BitacoraController(BitacoraService bitacoraService) {
		super();
		this.bitacoraService = bitacoraService;
	}
	
	@GetMapping("/bitacoras")
	public String listStudents(Model model) {
		model.addAttribute("bitacoras",bitacoraService.getAllBitacoras());
		return "bitacoras";
	}
	
	@GetMapping("/bitacoras/new")
	public String createBitacoraForm(Model model) {
		Bitacora bitacora= new Bitacora();
		model.addAttribute("bitacora",bitacora);
		return "create_bitacora";
	}
	
	@PostMapping("/bitacoras")
	public String saveBitacora(@ModelAttribute("bitacora") Bitacora bitacora) {
		
		bitacoraService.saveBitacora(bitacora);
		return "redirect:/bitacoras";
	}
	
	@GetMapping("/bitacoras/{id}")
	public String deleteBitacora (@PathVariable Integer id) {
		bitacoraService.deleteBitacoraById(id);
		return "redirect:/bitacoras";
	}
	
	@GetMapping("/bitacoras/edit/{id}")
	public String editBitacoraForm(@PathVariable Integer id, 
			                      Model model) {
		model.addAttribute("bitacoras",bitacoraService.getBitacoraById(id));
		return "edit_bitacora";
	}
	
	@PostMapping("/bitacoras/{id}")
	public String updateBitacora(@PathVariable Integer id, 
			                    @ModelAttribute ("bitacora") Bitacora bitacora, 
			                    Model model) {
		Bitacora existingBitacora= bitacoraService.getBitacoraById(id);
		existingBitacora.setCI_Id_Bitacora(id);
		existingBitacora.setCVUsuario(bitacora.getCVUsuario());
		existingBitacora.setCVDescripcion(bitacora.getCVDescripcion());
		bitacoraService.saveBitacora(existingBitacora);
		return "redirect:/bitacoras";
	}
	
	
}
