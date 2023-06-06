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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/hechoimputado")
    public String listHechoImputado(Model model){
        model.addAttribute("hechoImputado", hechoImputadoService.getAllHechoImputado());
        return "hechosImputados/hechoImputado";
    }

    @GetMapping("/hechosimputado/{id}")
    public String listHechosImputado(Model model, @PathVariable Integer id){
        model.addAttribute("hechoImputado", hechoImputadoService.getAllHechosImputado(id));
        return "hechosImputados/hechoImputado";
    }

    @GetMapping("/hechoimputados/{id}")
    public String listHechoImputados(Model model, @PathVariable Integer id){
        model.addAttribute("hechoImputado", hechoImputadoService.getAllHechoImputados(id));
        return "hechosImputados/hechoImputado";
    }

//    @GetMapping("hechoimputado/new")
//    public String createHechoImputadoForm(Model model){
//
//    	try {
//			this.validarPerfil();
//			if(!this.perfil.getCVRol().equals("Consulta")) {
//
//				HechoImputado hechoImputado = new HechoImputado();
//		        model.addAttribute("hechoImputado", hechoImputado);
//		        model.addAttribute("hechos", hechoService.getAllHechos());
//		        model.addAttribute("imputados", imputadoService.getAllImputados());
//		        return "hechosImputados/create_hecho_imputado";
//			}else {
//				return "SinAcceso";
//			}
//
//		}catch (Exception e) {
//			return "SinAcceso";
//		}
//    }

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
    public String deleteHecho(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
                hechoImputadoService.deleteHechoImputadoById(id);
				String descripcion = "Elimino en hechoImputado";
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
				bitacoraService.saveBitacora(bitacora);
		        return "redirect:/hechoimputado";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

//    @PostMapping("/hechoimputado")
//    public String saveHechoImputado(@ModelAttribute("hechoImputado") HechoImputado hechoImputado, Model model){
//        try {
//        	String descripcion="Creo en Hechos Imputado";
//		    Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
//            bitacoraService.saveBitacora(bitacora);
//
//            hechoImputadoService.saveHechoImputado(hechoImputado);
//            return "redirect:/hechoimputado";
//        }catch (DataIntegrityViolationException e){
//            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
//            model.addAttribute("error_message", mensaje);
//            model.addAttribute("error", true);
//            return createHechoImputadoForm(model);
//        }
//    }

    @PostMapping("/hechosimputado")
    public String saveHechosImputado(@ModelAttribute("hechoImputado") HechoImputado hechoImputado, Model model){
        try {
            hechoImputadoService.saveHechoImputado(hechoImputado);
            String descripcion="Creo en Hechos Imputado";
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechoimputado";
        }catch (DataIntegrityViolationException e){
            String mensaje = "No se puede guardar el hecho debido a un error de integridad de datos.";
            model.addAttribute("error_message", mensaje);
            model.addAttribute("error", true);
            return createHechosImputadoForm(model, hechoImputado.getCIHecho());
        }
    }

    @PostMapping("/hechoimputados")
    public String saveHechoImputados(@ModelAttribute("hechoImputado") HechoImputado hechoImputado, Model model){
        try {
            hechoImputadoService.saveHechoImputado(hechoImputado);
            String descripcion="Creo en Hechos Imputado";
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/hechoimputado";
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
            existingHechoImputado.setCI_Id(id);
            existingHechoImputado.setCIHecho(hechoImputado.getCIHecho());
            existingHechoImputado.setCIImputado(hechoImputado.getCIImputado());
            hechoImputadoService.updateHechoImputado(existingHechoImputado);
            String descripcion="Editó en Hechos Imputado";
            Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
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
