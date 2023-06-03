package com.if7100.controller;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;

@Controller
public class BitacoraController {

    private BitacoraService bitacoraService;
    //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    //instancias para control de bitacora
    private Usuario usuario;
  //  private Bitacora bitacora1;

    public BitacoraController(BitacoraService  bitacoraService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
        super();
        this.bitacoraService = bitacoraService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
      
       // this.bitacora1 = bitacora1;Bitacora bitacora1,
    }

private void validarPerfil() {

		try {
			Usuario usuario=new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));
			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

		}catch (Exception e) {
			// TODO: handle exception
		}

	}

    @GetMapping("/bitacoras")
    public String listBitacoras(Model model){
        model.addAttribute("bitacoras",  bitacoraService.getAllBitacoras());
        return "bitacoras/bitacoras";
    }

    @GetMapping("/bitacoras/new")
    public String createBitacorasForm(Model model){
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				Bitacora bitacora= new Bitacora();
		        model.addAttribute("bitacora", bitacora);
		        return "bitacoras/create_bitacora";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}

    }

    @PostMapping("/bitacoras")
    public String saveBitacora(@ModelAttribute("bitacora")Bitacora bitacora){
    	bitacoraService.saveBitacora(bitacora);
        return "redirect:/bitacoras";
    }

    @GetMapping("/bitacoras/{id}")
    public String deleteBitacora(@PathVariable Integer id){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				//INSERTAR EN BITACORA
				//String descripcion="Elimino en Bitacora";
			    //Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
               // bitacoraService.saveBitacora(bitacora);
                
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
    public String editBitacoraForm(@PathVariable Integer id, Model model){


    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("bitacora", bitacoraService.getBitacoraById(id));
		        return "bitacoras/edit_bitacora";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }


    @PostMapping("/bitacoras/{id}")
    public String updateModalidad(@PathVariable Integer id, @ModelAttribute("bitacora") Bitacora bitacora){
        Bitacora existingBitacora = bitacoraService.getBitacoraById(id);
        existingBitacora.setCIIdBitacora(id);
        existingBitacora.setCVUsuario(bitacora.getCVUsuario());
        existingBitacora.setCVDescripcion(bitacora.getCVDescripcion());
        existingBitacora.setCVRol(bitacora.getCVRol());
        existingBitacora.setCTFecha(new Date());
        bitacoraService.updateBitacora(existingBitacora);
        return "redirect:/bitacoras";
    }

}
