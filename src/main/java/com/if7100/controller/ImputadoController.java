package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.repository.UsuarioRepository;

@Controller
public class ImputadoController {
	
 private ImputadoService imputadoService;
//instancias para control de acceso
 private UsuarioRepository usuarioRepository;
 private Perfil perfil;
 private PerfilService perfilService;
//instancias para control de bitacora
private BitacoraService bitacoraService;
private Usuario usuario;

private OrientacionSexualService orientacionSexualService;

private OrientacionSexual orientacionSexual;
private IdentidadGeneroService identidadGeneroService;

private IdentidadGenero identidadGenero;

private NivelEducativo nivelEducativo;

private NivelEducativoService nivelEducativoService;

private SituacionJuridica situacionJuridica;

private SituacionJuridicaService situacionJuridicaService;

private Organismo organismo;

private OrganismoService organismoService;

 
 public ImputadoController (BitacoraService bitacoraService,
ImputadoService imputadoService, PerfilService perfilService, UsuarioRepository usuarioRepository,
							IdentidadGeneroService identidadGeneroService,OrientacionSexualService orientacionSexualService,
							NivelEducativoService nivelEducativoService,SituacionJuridicaService situacionJuridicaService,
							OrganismoService organismoService) {
	 super();
	 this.imputadoService=imputadoService;
	 this.perfilService = perfilService;
     this.usuarioRepository = usuarioRepository;
     this.bitacoraService= bitacoraService;
	 this.identidadGeneroService= identidadGeneroService;
	 this.orientacionSexualService= orientacionSexualService;
	 this.nivelEducativoService=nivelEducativoService;
	 this.situacionJuridicaService=situacionJuridicaService;
	 this.organismoService=organismoService;
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
 
 @GetMapping("/imputados")
 public String ListImputados(Model model) {

	 model.addAttribute("imputados",imputadoService.getAllImputados());
	 return "imputados/imputados";
 }
 
 @GetMapping("/imputados/new")
 public String CreateUsuarioForm(Model model) {

	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
//				String descripcion="Elimino en XXX/Crea en XXX/ Actualiza en XXX";
//				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
//				bitacoraService.saveBitacora(bitacora);
				model.addAttribute("orientacionSexual",orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero",identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("situacionJuridica",situacionJuridicaService.getAllSituacionJuridica());
				model.addAttribute("listaOrganismo",organismoService.getAllOrganismos());
				model.addAttribute("imputado",new Imputado());
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(),this.perfil.getCVRol(),"Crea en Imputado"));
				return "imputados/create_imputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @PostMapping("/imputados")
 public String SaveImputado(@ModelAttribute("usuario") Imputado imputado) {
	 imputadoService.saveImputado(imputado);
	 return "redirect:/imputados";
 }
 
 @GetMapping("/imputados/{id}")
 public String deleteUsuario(@PathVariable int id) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				

				imputadoService.deleteImputadoById(id);
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(),this.perfil.getCVRol(),"Eliminó en Imputado"));
				 return "redirect:/imputados";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
 }
 
 @GetMapping("/imputados/edit/{id}")
 public String editImputadosForm(Model model,@PathVariable int id) {
	 
	 try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				model.addAttribute("orientacionSexual",orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero",identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("situacionJuridica",situacionJuridicaService.getAllSituacionJuridica());
				model.addAttribute("listaOrganismo",organismoService.getAllOrganismos());
				model.addAttribute("imputado", imputadoService.getImputadoById(id));

				return "imputados/edit_imputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	 
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
	 existingImputado.setCVPais(imputado.getCVPais());
	 existingImputado.setCVNacionalidad(imputado.getCVNacionalidad());
	 existingImputado.setCVAntecedentes(imputado.getCVAntecedentes());
	 existingImputado.setCVEducacion(imputado.getCVEducacion());
	 existingImputado.setCVEstadoConyugal(imputado.getCVEstadoConyugal());
	 existingImputado.setCVLugarResidencia(imputado.getCVLugarResidencia());
	 existingImputado.setCVCondicionMigratoria(imputado.getCVCondicionMigratoria());
	 existingImputado.setCVEtnia(imputado.getCVEtnia());
	 existingImputado.setCVGenerador(imputado.getCVGenerador());
	 existingImputado.setCVOcupacion(imputado.getCVOcupacion());
	 existingImputado.setCVSituacionJuridica(imputado.getCVSituacionJuridica());
	 existingImputado.setCVPertenenciaFuerzaSeguridad(imputado.getCVPertenenciaFuerzaSeguridad());
	 existingImputado.setCVPermisoPortacionArmas(imputado.getCVPermisoPortacionArmas());
	 existingImputado.setCVSuicidio(imputado.getCVSuicidio());
	 existingImputado.setCVDomicilio(imputado.getCVDomicilio());
	 
	 imputadoService.updateImputado(existingImputado);
	 bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
			 this.usuario.getCVNombre(),this.perfil.getCVRol(),"Actualizó en Imputado"));
	 return "redirect:/imputados";
 }
 
 
 
 
}
