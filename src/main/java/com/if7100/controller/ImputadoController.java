package com.if7100.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Imputado;
import com.if7100.service.ImputadoService;

@Controller
public class ImputadoController {
	
 private ImputadoService imputadoService;
 
 public ImputadoController (ImputadoService imputadoService) {
	 super();
	 this.imputadoService=imputadoService;
 }
 
 @GetMapping("/imputados")
 public String ListImputados(Model model) {
	 
	 model.addAttribute("imputados",imputadoService.getAllUsuarios());
	 return "imputados";
 }
 
 @GetMapping("/imputados/new")
 public String CreateUsuarioForm(Model model) {
	 model.addAttribute("imputado",new Imputado());
	 return "create_imputado";
 }
 
 @PostMapping("/imputados")
 public String SaveImputado(@ModelAttribute("usuario") Imputado imputado) {
	 imputadoService.saveImputado(imputado);
	 return "redirect:/imputados";
 }
 
 @GetMapping("/imputados/{id}")
 public String deleteUsuario(@PathVariable int id) {
	 
	 imputadoService.deleteImputadoById(id);
	 return "redirect:/imputados";
 }
 
 @GetMapping("/imputados/edit/{id}")
 public String editImputadosForm(Model model,@PathVariable int id) {
	 
	 model.addAttribute("imputado", imputadoService.getImputadoById(id));
	 return "edit_imputado";
 }
 
 @PostMapping("/imputados/{id}")
 public String updateUsuario(@PathVariable int id, @ModelAttribute("imputado") Imputado imputado, Model model) {
	 Imputado existingImputado=imputadoService.getImputadoById(id);
	 existingImputado.setCVDni(imputado.getCVDni());
	 existingImputado.setCVGenero(imputado.getCVGenero());
	 existingImputado.setCVNombre(imputado.getCVNombre());
	 existingImputado.setCVOrientacionSexual(imputado.getCVOrientacionSexual());
	 existingImputado.setCVLugarNacimiento(imputado.getCVLugarNacimiento());
	 existingImputado.setCIEdad(imputado.getCIEdad());
	 
	 imputadoService.updateImputado(existingImputado);
	 return "redirect:/imputados";
 }
 
 
 
 
}
