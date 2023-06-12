/**
 * 
 */
package com.if7100.controller;

import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;
import com.if7100.service.IdentidadGeneroService;
import com.if7100.service.NivelEducativoService;
import com.if7100.service.OrganismoService;
import com.if7100.service.OrientacionSexualService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.service.PerfilService;

/**
 * @author Hadji
 *
 */


import com.if7100.service.VictimaService;
import com.if7100.entity.Hecho;
import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Organismo;
import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.Perfil;
import com.if7100.entity.Victima;
import com.if7100.repository.UsuarioRepository;

@Controller
public class VictimaController {
	
	private VictimaService victimaService;
	
	//instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    //Instancias para el control de organismo
    private Organismo organismo;
    private OrganismoService organismoService;
    //Instancias el control de Orientacion Sexual
    private OrientacionSexualService orientacionSexualService;
    private OrientacionSexual orientacionSexual;
  //Instancias el control de Genero
    private IdentidadGeneroService identidadGeneroService;
    private IdentidadGenero identidadGenero;
  //Instancias el control de Nivel educativo
    private NivelEducativo nivelEducativo;
    private NivelEducativoService nivelEducativoService;

	//Constructor con todos las instancias
	public VictimaController(VictimaService victimaService, UsuarioRepository usuarioRepository,
			PerfilService perfilService, BitacoraService bitacoraService, OrganismoService organismoService,
			OrientacionSexualService orientacionSexualService, IdentidadGeneroService identidadGeneroService,
			NivelEducativoService nivelEducativoService) {
		super();
		this.victimaService = victimaService;
		this.usuarioRepository = usuarioRepository;
		this.perfilService = perfilService;
		this.bitacoraService = bitacoraService;
		this.organismoService = organismoService;
		this.orientacionSexualService = orientacionSexualService;
		this.identidadGeneroService = identidadGeneroService;
		this.nivelEducativoService = nivelEducativoService;
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

	@GetMapping("/victima")
	public String listStudents(Model model) {
		model.addAttribute("victima", victimaService.getAllVictima());
		return "victimas/victima";
	}
	
	@GetMapping("/victima/new")
	public String createVictimaForm (Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				Victima victima = new Victima();
				
				model.addAttribute("orientacionSexual",orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero",identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("listaOrganismo",organismoService.getAllOrganismos());
				model.addAttribute("victima", victima);
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(),this.perfil.getCVRol(),"Crea en Victima"));
				return "victimas/create_victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	
	@PostMapping("/victima")
	public String saveVictima (@ModelAttribute("victima") Victima victima) {
		
		victimaService.saveVictima(victima);
		return "redirect:/victima";
	}
	
	@GetMapping("/victima/{Id}")
	public String deleteVictima (@PathVariable Integer Id) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				String descripcion = "Elimino una victima";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
				
				victimaService.deleteVictimaById(Id);
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
						this.usuario.getCVNombre(),this.perfil.getCVRol(),"Eliminó en Victima"));
				return "redirect:/victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}
	
	
	@GetMapping("/victima/edit/{id}")
	public String editVictimaForm (@PathVariable Integer id, Model model) {
		
		try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("orientacionSexual",orientacionSexualService.getAllOrientacionesSexuales());
				model.addAttribute("identidadGenero",identidadGeneroService.getAllIdentidadGenero());
				model.addAttribute("nivelEducativo",nivelEducativoService.getAllNivelEducativo());
				model.addAttribute("listaOrganismo",organismoService.getAllOrganismos());
				model.addAttribute("victima", victimaService.getVictimaById(id));
				return "victimas/edit_victima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}	
	}
	
	
	@PostMapping("/victima/{id}")
	public String updateVictima (@PathVariable Integer id, 
								 @ModelAttribute("victima") Victima victima,
								 Model model) {
		
		Victima existingVictima = victimaService.getVictimaById(id);
		existingVictima.setCI_Id(id);
		existingVictima.setCVDNI(victima.getCVDNI());
		existingVictima.setCVNombre(victima.getCVNombre());
		existingVictima.setCVApellidoPaterno(victima.getCVApellidoPaterno());
		existingVictima.setCVApellidoMaterno(victima.getCVApellidoMaterno());
		existingVictima.setCVEdad(victima.getCIEdad());
		existingVictima.setCVGenero(victima.getCVGenero());
		existingVictima.setCVLugarNac(victima.getCVLugarNac());
		existingVictima.setCVOrientaSex(victima.getCVOrientaSex());
		existingVictima.setCVNacionalidad(victima.getCVNacionalidad());
		existingVictima.setCIEducacion(victima.getCIEducacion());
		existingVictima.setCVOcupacion(victima.getCVOcupacion());
		existingVictima.setCVDomicilio(victima.getCVDomicilio());
		existingVictima.setCVLugarResidencia(victima.getCVLugarResidencia());
		existingVictima.setCVDiscapacidad(victima.getCVDiscapacidad());
		existingVictima.setCVCondicionMigratoria(victima.getCVCondicionMigratoria());
		existingVictima.setCVEtnia(victima.getCVEtnia());
		existingVictima.setCVMedidasProteccion(victima.getCVMedidasProteccion());
		existingVictima.setCVDenunciasPrevias(victima.getCVDenunciasPrevias());
		existingVictima.setCIHijos(victima.getCIHijos());
		existingVictima.setCVGenerador(victima.getCVGenerador());
		
		victimaService.updateVictima(existingVictima);
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCI_Id(),
				 this.usuario.getCVNombre(),this.perfil.getCVRol(),"Actualizó en Victima"));
		 
		return "redirect:/victima";
		
	}
	
}
