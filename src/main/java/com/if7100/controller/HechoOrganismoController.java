package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.BitacoraService;

import com.if7100.repository.UsuarioRepository;
import com.if7100.service.HechoService;
import com.if7100.service.OrganismoService;
import com.if7100.service.PerfilService;
import com.if7100.service.HechoOrganismoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.IntStream;

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

    private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos){
        int numeroPagina = pg-1;
        if (numeroTotalElementos < 10){
            paginasDeseadas = 1;
        }
        if (numeroTotalElementos < 1){
            numeroTotalElementos = 1;
        }
        int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
        return PageRequest.of(numeroPagina, tamanoPagina);
    }

    @GetMapping("/hechoorganismo")
    public String listHechoOrganismo(Model model){
       
        this.validarPerfil();
        return "redirect:/hechosorganismos/1";
    }

    @GetMapping("/hechosorganismos/{pg}")
    public String listHechosOrganismos(Model model, @PathVariable Integer pg){
       
        this.validarPerfil();
        
        if (pg < 1){
            return "redirect:/hechosorganismos/1";
        }

        int numeroTotalElementos = hechoOrganismoService.getAllHechoOrganismo().size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        Page<HechoOrganismo> hechoOrganismoPage = hechoOrganismoService.getAllHechoOrganismoPage(pageable);

        List<Integer> nPaginas = IntStream.rangeClosed(1, hechoOrganismoPage.getTotalPages()).boxed().toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechosorganismos", hechoOrganismoPage.getContent());
        model.addAttribute("listHechosOrganismos", true);
        return "hechosOrganismos/hechoOrganismo";

    }

    @GetMapping("/hechosorganismo/{id}")
    public String listHechosOrganismo(Model model, @PathVariable Integer id){
        return "redirect:/hechosorganismo/".concat(String.valueOf(id)).concat("/1");
    }

    @GetMapping("/hechosorganismo/{id}/{pg}")
    public String HechosOrganismo(Model model, @PathVariable Integer id, @PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/hechosorganismo/".concat(String.valueOf(id)).concat("/1");
        }

        int numeroTotalElementos = hechoOrganismoService.getAllHechosOrganismo(id).size();

        Pageable pageable = initPages(pg,5,numeroTotalElementos);

        Page<HechoOrganismo> hechoOrganismoPage = hechoOrganismoService.getAllHechosOrganismoPage(pageable, id);

        List<Integer> nPaginas = IntStream.rangeClosed(1, hechoOrganismoPage.getTotalPages()).boxed().toList();

        model.addAttribute("Id", id);
        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechosorganismos", hechoOrganismoPage.getContent());
        model.addAttribute("hechosOrganismo", true);
        return "hechosOrganismos/hechoOrganismo";
    }

    @GetMapping("/hechoorganismos/{id}")
    public String listHechoOrganismos(Model model, @PathVariable Integer id){
        return "redirect:/hechoorganismos/".concat(String.valueOf(id)).concat("/1");
    }

    @GetMapping("/hechoorganismos/{id}/{pg}")
    public String HechosOrganismos(Model model, @PathVariable Integer id, @PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/hechoOrganismos/".concat(String.valueOf(id)).concat("/1");
        }

        int numeroTotalElementos = hechoOrganismoService.getAllHechoOrganismos(id).size();

        Pageable pageable = initPages(pg,5,numeroTotalElementos);

        Page<HechoOrganismo> hechoOrganismoPage = hechoOrganismoService.getAllHechoOrganismosPage(pageable, id);

        List<Integer> nPaginas = IntStream.rangeClosed(1, hechoOrganismoPage.getTotalPages()).boxed().toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechosorganismos", hechoOrganismoPage.getContent());
        model.addAttribute("hechoOrganismos", true);
        return "hechosOrganismos/hechoOrganismo";
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
			     return "hechosOrganismos/create_hechos_organismo";
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
    public String deleteHechoOrganismo(@PathVariable Integer id){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoOrganismoService.deleteHechoOrganismoById(id);
                
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en hecho_organismo"));

                return "redirect:/hechoorganismo";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/hechoso/{id}/{idhecho}")
    public String deleteHechosorganismo(@PathVariable Integer id, @PathVariable Integer idhecho){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoOrganismoService.deleteHechoOrganismoById(id);
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en hecho_organismo"));

                return "redirect:/hechosorganismo/".concat(String.valueOf(idhecho));
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/horganismos/{id}/{idhecho}")
    public String deleteHechoOrganismos(@PathVariable Integer id, @PathVariable Integer idhecho){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoOrganismoService.deleteHechoOrganismoById(id);
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en hecho_organismo"));

                return "redirect:/hechoorganismos/".concat(String.valueOf(idhecho));
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/hechosorganismo")
    public String saveHechosOrganismo(@ModelAttribute HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            
            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en hecho_organismo"));

            return "redirect:/hechosorganismo/" + hechoOrganismo.getCIHecho();
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechosOrganismoForm(model, hechoOrganismo.getCIHecho());
        }
    }

    @PostMapping("/hechoorganismos")
    public String saveHechoOrganismos(@ModelAttribute HechoOrganismo hechoOrganismo, Model model){
        try {
            hechoOrganismoService.saveHechoOrganismo(hechoOrganismo);
            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en hecho_organismo"));

            return "redirect:/hechoorganismos/" + hechoOrganismo.getCIOrganismo();
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoOrganismosForm(model, hechoOrganismo.getCIOrganismo());
        }
    }

    @GetMapping("/hechoorganismo/edit/{id}")
    public String editHechoOrganismoForm(@PathVariable Integer id, Model model){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("hechoorganismo", hechoOrganismoService.getHechoOrganismoById(id));
                model.addAttribute("hechos", hechoService.getAllHechos());
                model.addAttribute("organismos", organismoService.getAllOrganismos());
                return "hechosOrganismos/edit_hecho_organismo";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/hechoorganismo/{id}")
    public String updateHechoOrganismo(@PathVariable Integer id, @ModelAttribute("hechoorganismo") HechoOrganismo hechoOrganismo, Model model){
        try {
            HechoOrganismo existingHechoOrganismo = hechoOrganismoService.getHechoOrganismoById(id);
            String descripcion = "Actualizo en hecho de organismo, de: " + existingHechoOrganismo.getCI_Id() + " | a: " + id;
            existingHechoOrganismo.setCI_Id(id);
            existingHechoOrganismo.setCIHecho(hechoOrganismo.getCIHecho());
            existingHechoOrganismo.setCIOrganismo(hechoOrganismo.getCIOrganismo());
            hechoOrganismoService.updateHechoOrganismo(existingHechoOrganismo);

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en hecho_organismo"));

            return "redirect:/hechoorganismo";
        } catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho - organismo debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return editHechoOrganismoForm(id, model);
        }
    }


}

