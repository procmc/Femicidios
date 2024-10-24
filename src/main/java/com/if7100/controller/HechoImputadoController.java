package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.entity.Hecho;
import com.if7100.entity.HechoImputado;
import com.if7100.entity.Lugar;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.HechoImputadoService;
import com.if7100.service.HechoService;
import com.if7100.service.ImputadoService;
import com.if7100.service.PerfilService;

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
public class HechoImputadoController {

    private HechoImputadoService hechoImputadoService;
    private HechoService hechoService;
    private ImputadoService imputadoService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;

    public HechoImputadoController(BitacoraService bitacoraService,HechoImputadoService hechoImputadoService, HechoService hechoService, ImputadoService imputadoService, PerfilService perfilService, UsuarioRepository usuarioRepository){
        super();
        this.hechoImputadoService = hechoImputadoService;
        this.hechoService = hechoService;
        this.imputadoService = imputadoService;
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

    @GetMapping("/hechoimputado")
    public String listHechoImputado(Model model){
        return "redirect:/hechosimputados/1";
    }

    @GetMapping("/hechosimputados/{pg}")
    public String listHechosImputados(Model model, @PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/hechosimputados/1";
        }
        int numeroTotalElementos = hechoImputadoService.getAllHechoImputado().size();

        Pageable pageable = initPages(pg,5,numeroTotalElementos);

        Page<HechoImputado> hechoImputadoPage = hechoImputadoService.getAllHechoImputadoPage(pageable);

        List<Integer> nPaginas = IntStream.rangeClosed(1, hechoImputadoPage.getTotalPages()).boxed().toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechosimputados", hechoImputadoPage.getContent());
        model.addAttribute("listHechosImputados", true);
        return "hechosImputados/hechoImputado";
    }

    @GetMapping("/hechosimputado/{id}")
    public String listHechosImputado(Model model, @PathVariable Integer id){
        return "redirect:/hechosimputado/".concat(String.valueOf(id)).concat("/1");
    }

    @GetMapping("/hechosimputado/{id}/{pg}")
    public String HechosImputado(Model model, @PathVariable Integer id, @PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/hechosimputado/".concat(String.valueOf(id)).concat("/1");
        }

        int numeroTotalElementos = hechoImputadoService.getAllHechosImputado(id).size();

        Pageable pageable = initPages(pg,5,numeroTotalElementos);

        Page<HechoImputado> hechoImputadoPage = hechoImputadoService.getAllHechosImputadoPage(pageable, id);

        List<Integer> nPaginas = IntStream.rangeClosed(1, hechoImputadoPage.getTotalPages()).boxed().toList();

        model.addAttribute("Id", id);
        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechosimputados", hechoImputadoPage.getContent());
        model.addAttribute("hechosImputado", true);
        return "hechosImputados/hechoImputado";
    }

    @GetMapping("/hechoimputados/{id}")
    public String listHechoImputados(Model model, @PathVariable Integer id){
        return "redirect:/hechoimputados/".concat(String.valueOf(id)).concat("/1");
    }

    @GetMapping("/hechoimputados/{id}/{pg}")
    public String HechosImputados(Model model, @PathVariable Integer id, @PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/hechoimputados/".concat(String.valueOf(id)).concat("/1");
        }

        int numeroTotalElementos = hechoImputadoService.getAllHechoImputados(id).size();

        Pageable pageable = initPages(pg,5,numeroTotalElementos);

        Page<HechoImputado> hechoImputadoPage = hechoImputadoService.getAllHechoImputadosPage(pageable, id);

        List<Integer> nPaginas = IntStream.rangeClosed(1, hechoImputadoPage.getTotalPages()).boxed().toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("hechosimputados", hechoImputadoPage.getContent());
        model.addAttribute("hechoImputados", true);
        return "hechosImputados/hechoImputado";
    }

    @GetMapping("/hechosimputado/new/{Id}")
    public String createHechosImputadoForm(Model model, @PathVariable Integer Id) {
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				HechoImputado hechoImputado = new HechoImputado();
		        hechoImputado.setCIHecho(Id);
		        model.addAttribute("hechoImputado", hechoImputado);
		        model.addAttribute("hechos", hechoService.getAllHechos());
		        model.addAttribute("imputados", imputadoService.getAllImputados());
		        return "hechosImputados/create_hechos_imputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechoimputados/new/{Id}")
    public String createHechoImputadosForm(Model model, @PathVariable Integer Id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				 HechoImputado hechoImputado = new HechoImputado();
			     hechoImputado.setCIImputado(Id);
			     model.addAttribute("hechoImputado", hechoImputado);
			     model.addAttribute("hechos", hechoService.getAllHechos());
			     model.addAttribute("imputados", imputadoService.getImputadoById(Id));
			     return "hechosImputados/create_hecho_imputados";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechoimputado/{id}")
    public String deleteHechoImputado(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoImputadoService.deleteHechoImputadoById(id);
				String descripcion = "Elimino en hechoImputado: " + id;
                Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
                bitacoraService.saveBitacora(bitacora);
		        return "redirect:/hechoimputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/hechosi/{id}/{idhecho}")
    public String deleteHechosimputado(@PathVariable Integer id, @PathVariable Integer idhecho){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoImputadoService.deleteHechoImputadoById(id);
                String descripcion = "Elimino en hechoImputado: " + id;
                Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
                bitacoraService.saveBitacora(bitacora);
                return "redirect:/hechosimputado/".concat(String.valueOf(idhecho));
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/himputados/{id}/{idhecho}")
    public String deleteHechoImputados(@PathVariable Integer id, @PathVariable Integer idhecho){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoImputadoService.deleteHechoImputadoById(id);
                String descripcion = "Elimino en hechoImputado: " + id;
                Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
                bitacoraService.saveBitacora(bitacora);
                return "redirect:/hechoimputados/".concat(String.valueOf(idhecho));
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/hechosimputado")
    public String saveHechosImputado(@ModelAttribute HechoImputado hechoImputado, Model model){
        try {
            hechoImputadoService.saveHechoImputado(hechoImputado);
            String descripcion="Creo en Hechos Imputado: " + hechoImputado.getCI_Id();
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechosimputado/" + hechoImputado.getCIHecho();
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechosImputadoForm(model, hechoImputado.getCIHecho());
        }
    }

    @PostMapping("/hechoimputados")
    public String saveHechoImputados(@ModelAttribute HechoImputado hechoImputado, Model model){
        try {
            hechoImputadoService.saveHechoImputado(hechoImputado);
            String descripcion="Creo en Hechos Imputado: " + hechoImputado.getCI_Id();
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechoimputados/" + hechoImputado.getCIImputado();
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechoImputadosForm(model, hechoImputado.getCIImputado());
        }
    }

    @GetMapping("/hechoimputado/edit/{id}")
    public String editHechoImputadoForm(@PathVariable Integer id, Model model){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("hechoimputado", hechoImputadoService.getHechoImputadoById(id));
                model.addAttribute("hechos", hechoService.getAllHechos());
                model.addAttribute("imputados", imputadoService.getAllImputados());
                return "hechosImputados/edit_hecho_imputado";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/hechoimputado/{id}")
    public String updateHechoImputado(@PathVariable Integer id, @ModelAttribute("hechoimputado") HechoImputado hechoImputado, Model model){
        try {
            HechoImputado existingHechoImputado = hechoImputadoService.getHechoImputadoById(id);
            String descripcion="Editó en Hechos Imputado, de: " + existingHechoImputado.getCI_Id() + " | a: " + id;
            existingHechoImputado.setCI_Id(id);
            existingHechoImputado.setCIHecho(hechoImputado.getCIHecho());
            existingHechoImputado.setCIImputado(hechoImputado.getCIImputado());
            hechoImputadoService.updateHechoImputado(existingHechoImputado);

            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechoimputado";
        } catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho - imputado debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return editHechoImputadoForm(id, model);
        }
    }


}
