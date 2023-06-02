package com.if7100.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.HechoOrganismo;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.HechoOrganismoService;
import com.if7100.service.HechoService;
import com.if7100.service.OrganismoService;
import com.if7100.service.PerfilService;

@Controller
public class HechoOrganismoController {

    private HechoOrganismoService hechoOrganismoService;
    private HechoService hechoService;

    private OrganismoService organismoService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


    public HechoOrganismoController(BitacoraService bitacoraService,
HechoOrganismoService hechoOrganismoService, HechoService hechoService, OrganismoService organismoService, PerfilService perfilService, UsuarioRepository usuarioRepository){
        super();
        this.hechoOrganismoService = hechoOrganismoService;
        this.hechoService = hechoService;
        this.organismoService = organismoService;
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

    @GetMapping("/hechoorganismo")
    public String listHechoImputado(Model model){
        model.addAttribute("hechoOrganismo", hechoOrganismoService.getAllHechoOrganismo());
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("/hechosorganismo/{id}")
    public String listHechosImputado(Model model, @PathVariable Integer id){
        model.addAttribute("hechoOrganismo", hechoOrganismoService.getAllHechosOrganismo(id));
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("/hechoorganismos/{id}")
    public String listHechoImputados(Model model, @PathVariable Integer id){
        model.addAttribute("hechoOrganismo", hechoOrganismoService.getAllHechoOrganismos(id));
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("hechoorganismo/new")
    public String createHechoOrganismoForm(Model model){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				 HechoOrganismo hechoOrganismo = new HechoOrganismo();
			     model.addAttribute("hechoOrganismo", hechoOrganismo);
			     model.addAttribute("hechos", hechoService.getAllHechos());
			     model.addAttribute("organismos", organismoService.getAllOrganismos());
			     return "hechosOrganismos/create_hecho_organismo";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechosorganismo/new/{Id}")
    public String createHechosOrganismoForm(Model model, @PathVariable Integer Id) {

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				 HechoOrganismo hechoOrganismo = new HechoOrganismo();
			     hechoOrganismo.setCIHecho(Id);
			     model.addAttribute("hechoOrganismo", hechoOrganismo);
			     model.addAttribute("hechos", hechoService.getAllHechos());
			     model.addAttribute("organismos", organismoService.getAllOrganismos());
			     return "hechosOrganismos/create_hechos_Organismo";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechoorganismos/new/{Id}")
    public String createHechoOrganismosForm(Model model, @PathVariable Integer Id){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				 HechoOrganismo hechoOrganismo = new HechoOrganismo();
			        hechoOrganismo.setCIOrganismo(Id);
			        model.addAttribute("hechoOrganismo", hechoOrganismo);
			        model.addAttribute("hechos", hechoService.getAllHechos());
			        model.addAttribute("organismos", organismoService.getAllOrganismos());
			        return "hechosOrganismos/create_hecho_organismos";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechoorganismo/{id}")
    public String deleteHecho(@PathVariable Integer id){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				//Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
				//bitacoraService.saveBitacora(bitacora);

				hechoOrganismoService.deleteHechoOrganismoById(id);
		        return "redirect:/hechoorganismo";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/hechoorganismo")
    public String saveHechoOrganismo(@ModelAttribute("hechoOrganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            return "redirect:/hechoorganismo";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoOrganismoForm(model);
        }
    }

    @PostMapping("/hechosorganismo")
    public String saveHechosOrganismo(@ModelAttribute("hechoOrganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            return "redirect:/hechoorganismo";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechosOrganismoForm(model, hechoOrganismo.getCIHecho());
        }
    }

    @PostMapping("/hechoorganismos")
    public String saveHechoOrganismos(@ModelAttribute("hechoOrganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            return "redirect:/hechoorganismo";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoOrganismosForm(model, hechoOrganismo.getCIOrganismo());
        }
    }


}

