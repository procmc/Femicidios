/**
 * 
 */
package com.if7100.controller;


import org.springframework.web.bind.annotation.GetMapping;

import java.security.Timestamp;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;

/**
 * @author tisha
 *
 */


@Controller
public class BitacoraController {

	private BitacoraService bitacoraService;
	//instancias para control de acceso
	private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;

	public BitacoraController(BitacoraService bitacoraService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
		super();
		this.bitacoraService = bitacoraService;
		this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
	}
	
private void validarPerfil() {
     	
		try {
			Usuario usuario=new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@GetMapping("/bitacoras")
	public String listStudents(Model model) {
		model.addAttribute("bitacoras",bitacoraService.getAllBitacoras());
		return "bitacoras/bitacoras";
	}
	
	@GetMapping("/bitacoras/new")
	public String createBitacoraForm(Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				Bitacora bitacora= new Bitacora();
				model.addAttribute("bitacora",bitacora);
				return "bitacoras/create_bitacora";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/bitacoras")
	public String saveBitacora(@ModelAttribute("bitacora") Bitacora bitacora) {
		
		bitacoraService.saveBitacora(bitacora);
		return "redirect:/bitacoras";
	}
	
	@GetMapping("/bitacoras/{id}")
	public String deleteBitacora (@PathVariable Integer id) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				bitacoraService.deleteBitacoraById(id);
				return "redirect:/bitacoras";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@GetMapping("/bitacoras/edit/{id}")
	public String editBitacoraForm(@PathVariable Integer id, 
			                      Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("bitacoras",bitacoraService.getBitacoraById(id));
				return "bitacoras/edit_bitacora";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	@PostMapping("/bitacoras/{id}")
	public String updateBitacora(@PathVariable Integer id, 
			                    @ModelAttribute ("bitacora") Bitacora bitacora, 
			                    Model model) {
		Bitacora existingBitacora= bitacoraService.getBitacoraById(id);
		existingBitacora.setCI_Id_Bitacora(id);
	//	existingBitacora.setCIId(bitacora.getCIId());
		existingBitacora.setCVUsuario(bitacora.getCVUsuario());
		existingBitacora.setCVRol(bitacora.getCVRol());
		existingBitacora.setCVDescripcion(bitacora.getCVDescripcion());
		//existingBitacora.setCTFecha(bitacora.getCTFecha());
		bitacoraService.saveBitacora(existingBitacora);
		return "redirect:/bitacoras";
	}
	
	
	
}
