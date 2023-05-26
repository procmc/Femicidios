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
    private Bitacora bitacora;
    private BitacoraService bitacoraService;
    private Usuario usuario;

    public ModalidadController(ModalidadService modalidadService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
        super();
        this.modalidadService = modalidadService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
    }
    
private void validarPerfil() {
    	
		try {
			Usuario usuario=new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    //String username = authentication.getName();
			String username = authentication.getName();
			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));
			//this.usuarioRepository.findById(usuario.getCI_Id());
			
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

        //Modalidad modalidad = new Modalidad();
        //model.addAttribute("modalidad", modalidad);
        //return "modalidades/create_modalidad";

    	
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
        modalidadService.saveModalidad(modalidad);
        return "redirect:/modalidades";
    }

    @GetMapping("/modalidades/{id}")
    public String deleteModalidad(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				/*bitacora.Conexion();
				String query = "Insert into th_bitacoras "+ 
				     "(`CI_Id`,`CV_DNI_Usuario`,`CV_Descripcion`,`CT_Fecha`) values "
				     + "('"+ usuario.getCI_Id() +"')";
				Statement stmt= bitacora.getConexionBD().createStatement();
				stmt.executeUpdate(query);
				*/
				//bitacoraService.crearNuevaBitacora(bitacora);
				
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

        //model.addAttribute("modalidad", modalidadService.getModalidadById(id));
        //return "modalidades/edit_modalidad";

    	
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
        Modalidad existingModalidad = modalidadService.getModalidadById(id);
        existingModalidad.setCI_Codigo(id);
        existingModalidad.setCVTitulo(modalidad.getCVTitulo());
        existingModalidad.setCVDescripcion(modalidad.getCVDescripcion());
        modalidadService.updateModalidad(existingModalidad);
        return "redirect:/modalidades";
    }

}
