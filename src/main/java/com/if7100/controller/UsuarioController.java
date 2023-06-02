/**
 *
 */
package com.if7100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.if7100.service.UsuarioService;

import jakarta.validation.Valid;


@Controller
public class UsuarioController {
	@Autowired

	private UsuarioService usuarioService;
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


//prueba
//private SessionRegistry sessionRegistry;

 public UsuarioController(BitacoraService bitacoraService,
UsuarioService usuarioService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
	 super();
	 this.usuarioService=usuarioService;
	 this.perfilService = perfilService;
     this.usuarioRepository = usuarioRepository;
     this.bitacoraService= bitacoraService;

 }

 private void validarPerfil() {

		try {
			Usuario usuario=new Usuario();

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
		    this.usuario= new Usuario(usuarioRepository.findByCVCedula(username));

			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

		}catch (Exception e) {
			// TODO: handle exception
		}

	}

 @GetMapping("/usuarios")
 public String listStudents(Model model) {

	 try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {

				 model.addAttribute("usuarios",usuarioService.getAllUsuarios());
				 return "usuarios/usuarios";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }


 // creacion de un nuevo usuario
 @GetMapping("/usuarios/new")
 public String createUsuarioForm(Model model) {

	 try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {

				Usuario usuario=new Usuario();
				 model.addAttribute("usuario", usuario);
				 return "usuarios/create_usuario";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }




 @PostMapping("/usuarios")
 public String saveUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result) {
	 if(result.hasErrors()) {
		 return "redirect:/create_usuario";
	 }else {
		 usuarioService.saveUsuario(usuario);
		 return "redirect:/usuarios";
	 }
 }






 //Eliminar Usuario
 @GetMapping("/usuarios/{Id}")
 public String deleteUsuario(@PathVariable Integer Id) {

	 try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {
				Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);

				usuarioService.deleteUsuarioById(Id);
				return "redirect:/usuarios?Exito";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }






 //EditarUsuario
 @GetMapping("/usuarios/edit/{Id}")
 public String editUsuarioForm(@PathVariable Integer Id, Model model) {

	 try {
			this.validarPerfil();
			if(this.perfil.getCVRol().equals("Administrador")) {

				model.addAttribute("usuario", usuarioService.getUsuarioById(Id));
				return "usuarios/edit_usuario";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
 }

 @PostMapping("/usuarios/{Id}")
 public String updateUsuario(@PathVariable Integer Id, @ModelAttribute("usuario") Usuario usuario) {
	 Usuario existingUsuario=usuarioService.getUsuarioById(Id);
	 existingUsuario.setCI_Id(Id);
	 existingUsuario.setCVCedula(usuario.getCVCedula());
	 existingUsuario.setCVNombre(usuario.getCVNombre());
	 existingUsuario.setCVApellidos(usuario.getCVApellidos());
	 existingUsuario.setCIPerfil(usuario.getCIPerfil());
	 existingUsuario.setTCClave(usuario.getTCClave());
	 usuarioService.updateUsuario(existingUsuario);
	 return "redirect:/usuarios";
  }
 /*
 @GetMapping("/session")
 public  ResponseEntity<?> getDetailsSession(){
	 String sessionId="";
	 User userObject=null;


	 List<Object> sessions= sessionRegistry.getAllPrincipals();

	 for(Object session : sessions) {
		 if(session instanceof User) {
			 userObject=(User) session;
		 }
		List<SessionInformation> sessionInformations= sessionRegistry.getAllSessions(session, false);
		 for(SessionInformation sessionInformation : sessionInformations) {
			 sessionId=sessionInformation.getSessionId();
		 }
	 }

	 Map<String, Object> response= new HashMap<>();
	 response.put("response","Hello");
	 response.put("sessionId",sessionId);
	 response.put("sessionUser",userObject);
	 return ResponseEntity.ok(response);
 }*/

}
