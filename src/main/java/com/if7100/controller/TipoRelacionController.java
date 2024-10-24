package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.entity.Hecho;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionService;

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
public class TipoRelacionController {


    private TipoRelacionService tipoRelacionService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    private PaisesService paisesService;

  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    private Paises paises;



    public TipoRelacionController(BitacoraService bitacoraService,
TipoRelacionService tipoRelacionService, PerfilService perfilService, UsuarioRepository usuarioRepository, PaisesService paisesService) {
        super();
        this.tipoRelacionService = tipoRelacionService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService= bitacoraService;
        this.paisesService = paisesService;


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

    @GetMapping("/tiporelaciones")
    public String listTipoRelaciones(Model model){
        return "redirect:/tiporelacion/1";
    }

    @GetMapping("/tiporelacion/{pg}")
    public String listTipoRelacion(Model model,@PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/tiporelaciones";
        }

        int numeroTotalElementos = tipoRelacionService.getAllTipoRelaciones().size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        Page<TipoRelacion> tipoRelacionPage = tipoRelacionService.getAllTipoRelacionesPage(pageable);

        List<Integer> nPaginas = IntStream.rangeClosed(1, tipoRelacionPage.getTotalPages())
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tiporelaciones", tipoRelacionPage.getContent());
        return "tipoRelaciones/tipoRelaciones";
    }

    @GetMapping("/tiporelaciones/new")
    public String createTipoRelacionForm(Model model){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				TipoRelacion tipoRelacion = new TipoRelacion();
		        model.addAttribute("tipoRelacion", tipoRelacion);
		        model.addAttribute("paises", paisesService.getAllPaises());

		        return "tipoRelaciones/create_tipoRelacion";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}     
    }

    @PostMapping("/tiporelaciones")
    public String saveTipoRelacion(@ModelAttribute("tipoRelacion") TipoRelacion tipoRelacion){
        tipoRelacionService.saveTipoRelacion(tipoRelacion);
        String descripcion="Crea un Tipo de Relacion: ID " + tipoRelacion.getCI_Codigo();
        Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), this.perfil.getCVRol(), descripcion);
        bitacoraService.saveBitacora(bitacora);
        
        return "redirect:/tiporelaciones";
    }

    @GetMapping("/tiporelaciones/{id}")
    public String deleteTipoRelaciones(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				String descripcion = "Se eliminó el tipo de relacion ID: " + id;
				Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(), descripcion);
				bitacoraService.saveBitacora(bitacora);
				
				tipoRelacionService.deleteTipoRelacionById(id);
		        return "redirect:/tiporelaciones";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/tiporelaciones/edit/{id}")
    public String editTipoRelacionForm(@PathVariable Integer id, Model model){
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				model.addAttribute("tipoRelacion", tipoRelacionService.getTipoRelacionById(id));
				model.addAttribute("paises", paisesService.getAllPaises());

		        return "tipoRelaciones/edit_tipoRelacion";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/tiporelaciones/{id}")
    public String updateTipoRelacion(@PathVariable Integer id, @ModelAttribute("tipoRelacion") TipoRelacion tipoRelacion, Model model){
        TipoRelacion existingTipoRelacion = tipoRelacionService.getTipoRelacionById(id);
    	String descripcion="Actualizó en Tipo Relación: ID " + id;
		Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
		bitacoraService.saveBitacora(bitacora);
		
        model.addAttribute("paises", paisesService.getAllPaises());

        existingTipoRelacion.setCI_Codigo(id);
        existingTipoRelacion.setCVTitulo(tipoRelacion.getCVTitulo());
        existingTipoRelacion.setCVDescripcion(tipoRelacion.getCVDescripcion());
        existingTipoRelacion.setCVPais(tipoRelacion.getCVPais());

        tipoRelacionService.updateTipoRelacion(existingTipoRelacion);
        return "redirect:/tiporelaciones";
    }
}
