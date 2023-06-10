package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.entity.Usuario;
import com.if7100.entity.Modalidad;
import com.if7100.entity.Perfil;

import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.ModalidadService;
import com.if7100.service.PerfilService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ModalidadController {

    private ModalidadService modalidadService;
    //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;

    public ModalidadController(ModalidadService modalidadService, PerfilService perfilService, UsuarioRepository usuarioRepository, BitacoraService bitacoraService) {
        super();
        this.modalidadService = modalidadService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
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

    @GetMapping("/modalidades")
    public String listModalidades(Model model){
        model.addAttribute("modalidades", modalidadService.getAllModalidades());
        return "modalidades/modalidades";
    }

    @GetMapping("/modalidades/new")
    public String createModalidadForm(Model model){

    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				Modalidad modalidad= new Modalidad();
		        model.addAttribute("modalidad", modalidad);
		        return "modalidades/create_modalidad";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}

    }

    @PostMapping("/modalidades")
    public String saveModalidad(@ModelAttribute("modalidad")Modalidad modalidad){
    	//INSERTAR EN BITACORA
		String descripcion = "Creo una modalidad";
		Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
		bitacoraService.saveBitacora(bitacora);
		
        modalidadService.saveModalidad(modalidad);
        return "redirect:/modalidades";
    }

    @GetMapping("/modalidades/{id}")
    public String deleteModalidad(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				//INSERTAR EN BITACORA
				String descripcion = "Elimino una modalidad";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
				
				bitacoraService.saveBitacora(bitacora);
				modalidadService.deleteModalidadById(id);
		        return "redirect:/modalidades";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/modalidades/edit/{id}")
    public String editModalidadForm(@PathVariable Integer id, Model model){

    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("modalidad", modalidadService.getModalidadById(id));
		        return "modalidades/edit_modalidad";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }
    

    @PostMapping("/modalidades/{id}")
    public String updateModalidad(@PathVariable Integer id, @ModelAttribute("modalidad") Modalidad modalidad){
    	//INSERTAR EN BITACORA
    			String descripcion = "Actualizo una modalidad";
    			Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
    			bitacoraService.saveBitacora(bitacora);
    			
        Modalidad existingModalidad = modalidadService.getModalidadById(id);
        existingModalidad.setCI_Codigo(id);
        existingModalidad.setCVTitulo(modalidad.getCVTitulo());
        existingModalidad.setCVDescripcion(modalidad.getCVDescripcion());
        modalidadService.updateModalidad(existingModalidad);
        return "redirect:/modalidades";
    }

}
