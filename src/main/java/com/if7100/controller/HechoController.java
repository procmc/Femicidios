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

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.HechoService;
import com.if7100.service.ModalidadService;
import com.if7100.service.PerfilService;
import com.if7100.service.ProcesoJudicialService;
import com.if7100.service.TipoRelacionService;
import com.if7100.service.TipoVictimaService;
import com.if7100.service.VictimaService;

@Controller
public class HechoController {

    private HechoService hechoService;
    private ModalidadService modalidadService;
    private TipoVictimaService tipoVictimaService;
    private TipoRelacionService tipoRelacionService;

    private VictimaService victimaService;

    private ProcesoJudicialService procesoJudicialService;

  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


//    public HechoController(HechoService hechoService) {
//        super();
//        this.hechoService = hechoService;
//    }

    public HechoController(HechoService hechoService, ModalidadService modalidadService,
                           TipoVictimaService tipoVictimaService, TipoRelacionService tipoRelacionService,
                           VictimaService victimaService, ProcesoJudicialService procesoJudicialService, PerfilService perfilService, UsuarioRepository usuarioRepository,
                           BitacoraService bitacoraService)
{
        super();
        this.hechoService = hechoService;
        this.modalidadService = modalidadService;
        this.tipoVictimaService = tipoVictimaService;
        this.tipoRelacionService = tipoRelacionService;
        this.victimaService = victimaService;
        this.procesoJudicialService = procesoJudicialService;
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

    @GetMapping("/hechos")
    public String listHechos(Model model){
        model.addAttribute("hechos", hechoService.getAllHechos());
        return "hechos/hechos";
    }

    @GetMapping("/hechos/new")
    public String createHechoForm(Model model){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				Hecho hecho = new Hecho();
		        model.addAttribute("hecho", hecho);
		        model.addAttribute("modalidad", modalidadService.getAllModalidades());
		        model.addAttribute("tipoVictima", tipoVictimaService.getAllTipoVictimas());
		        model.addAttribute("tipoRelacion", tipoRelacionService.getAllTipoRelaciones());
		        model.addAttribute("victima", victimaService.getAllVictima());
		        model.addAttribute("proceso", procesoJudicialService.getAllProcesosJudiciales());
		        return "hechos/create_hecho";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

//    @PostMapping("/guardar")
//    public String guardarHecho(@ModelAttribute("hecho") Hecho hecho, BindingResult result, Model model) {
//        try {
//            hechoRepository.save(hecho);
//            return "redirect:/hechos";
//        } catch (DataIntegrityViolationException e) {
//            // Manejar la excepción aquí
//            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
//            model.addAttribute("error", mensaje);
//            return "formularioHecho";
//        }
//    }

    @PostMapping("/hechos")
    public String saveHecho(@ModelAttribute("hecho") Hecho hecho, Model model){
        try {
            hechoService.saveHecho(hecho);
            return "redirect:/hechos";
        } catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoForm(model);
        }
    }

    @GetMapping("/hechos/{id}")
    public String deleteHecho(@PathVariable Integer id){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				try {

					Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
					bitacoraService.saveBitacora(bitacora);


		            hechoService.deleteHechoById(id);
		        } catch (DataIntegrityViolationException e) {
		            System.out.println("Error, No se puede eliminar un hecho si tiene lugares registrados en el");
		        }
		        return "redirect:/hechos";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechos/edit/{id}")
    public String editHechoForm(@PathVariable Integer id, Model model){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

				model.addAttribute("hecho", hechoService.getHechoById(id));
		        model.addAttribute("modalidad", modalidadService.getAllModalidades());
		        model.addAttribute("tipoVictima", tipoVictimaService.getAllTipoVictimas());
		        model.addAttribute("tipoRelacion", tipoRelacionService.getAllTipoRelaciones());
		        model.addAttribute("victima", victimaService.getAllVictima());
		        model.addAttribute("proceso", procesoJudicialService.getAllProcesosJudiciales());
		        return "hechos/edit_hecho";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/hechos/{id}")
    public String updateHecho(@PathVariable Integer id, @ModelAttribute("hecho") Hecho hecho, Model model){
        try {
            Hecho existingHecho = hechoService.getHechoById(id);
            existingHecho.setCI_Id(id);
            existingHecho.setCIPais(hecho.getCIPais());
            existingHecho.setCITipoVictima(hecho.getCITipoVictima());
            existingHecho.setCITipoRelacion(hecho.getCITipoRelacion());
            existingHecho.setCIModalidad(hecho.getCIModalidad());
            existingHecho.setCIIdVictima(hecho.getCIIdVictima());
            existingHecho.setCIIdProceso(hecho.getCIIdProceso());
            hechoService.updateHecho(existingHecho);
            return "redirect:/hechos";
        } catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return editHechoForm(id, model);
        }
    }
}
