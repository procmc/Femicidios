/**
 * 
 */
package com.if7100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Liss
 * Fecha: 11 de abril del 2023
 */
import com.if7100.entity.Usuario;
import com.if7100.service.UsuarioService;
import jakarta.validation.Valid;
   

@Controller
public class UsuarioController {
	@Autowired
	
private UsuarioService usuarioService;

//prueba
//private SessionRegistry sessionRegistry;
 
 public UsuarioController(UsuarioService usuarioService) {
	 super();
	 this.usuarioService=usuarioService;
 }
 
 @GetMapping("/usuarios")
 public String listStudents(Model model) {
	 model.addAttribute("usuarios",usuarioService.getAllUsuarios());
	 return "usuarios";
 }
 
 
 // creacion de un nuevo usuario
 @GetMapping("/usuarios/new")
 public String createUsuarioForm(Model model) {
	 Usuario usuario=new Usuario();
	 model.addAttribute("usuario", usuario);
	 return "create_usuario";
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
	 usuarioService.deleteUsuarioById(Id);
	 return "redirect:/usuarios?Exito";
 }
 
 
 
 
 
 
 //EditarUsuario
 @GetMapping("/usuarios/edit/{Id}")
 public String editUsuarioForm(@PathVariable Integer Id, Model model) {
	 model.addAttribute("usuario", usuarioService.getUsuarioById(Id));
	 return "edit_usuario";
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
