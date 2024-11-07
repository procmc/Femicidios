package com.if7100.controller;

import com.if7100.entity.Bitacora; 
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.service.BitacoraService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;//para el controlador
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.service.PerfilService;
/**
 * @author kendall B
 * Fecha: 11 de abril del 2023
 */
import com.if7100.service.TipoLugarService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.entity.Hecho;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoLugar;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class TipoLugarController {

    private TipoLugarService tipoLugarService;
	private UsuarioPerfilService usuarioPerfilService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


    public TipoLugarController(BitacoraService bitacoraService,
TipoLugarService tipoLugarService, PerfilService perfilService, UsuarioRepository usuarioRepository,
UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository) {
        super();
        this.tipoLugarService= tipoLugarService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService= bitacoraService;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;

    }
    
    private void validarPerfil() {
    	
		try {
			Usuario usuario=new Usuario();

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
		    this.usuario= new Usuario(usuarioRepository.findByCVCedula(username));

			List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilRepository.findByUsuario(this.usuario);

            this.perfil = new Perfil(
                    perfilService.getPerfilById(usuarioPerfiles.get(0).getPerfil().getCI_Id()));

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

    //consultar
    @GetMapping("/tipolugares")//muestra el listado de usuarios
    public String listTipoLugares(Model model) {
		this.validarPerfil();
		return "redirect:/tipolugar/1";
    }

	@GetMapping("/tipolugar/{pg}")
	public String listTiposLugares(Model model, @PathVariable Integer pg){
        this.validarPerfil();
		if (pg < 1){
			return "redirect:/tipolugar/1";
		}

		int numeroTotalElementos = tipoLugarService.getAllTipoLugares().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		Page<TipoLugar> tipoLugarPage = tipoLugarService.getAllTipoLugaresPage(pageable);

		List<Integer> nPaginas = IntStream.rangeClosed(1, tipoLugarPage.getTotalPages())
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("tipoLugares", tipoLugarPage.getContent());
		return "tipoLugares/tipoLugares";
	}

	//agregar
	@GetMapping("/tipolugares/new")// envia el modelo a la pagina de crear usuario
	public String CreateTipoLugarForm (Model model) {
		
		try {
			this.validarPerfil();
			if((usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2))) {
				
				TipoLugar tipoLugar= new TipoLugar();
				model.addAttribute("tipoLugar", tipoLugar);
				return "tipoLugares/create_tipoLugar";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/tipolugares")// guarda el usuario y lo devuelve a la pagina usuarios con los datos nuevos
	public String saveTipoLugar (@ModelAttribute TipoLugar tipoLugar) {
		this.validarPerfil();
		tipoLugarService.saveTipoLugar(tipoLugar);
	    
	    //funcionalidad de bitacora
	    bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en tipos de lugares", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/tipolugares";
	}

	//eliminar
	@GetMapping("/tipolugares/{Codigo}")// envia el modelo a la pagina de crear usuario
	public String deleteTipoLugar(@PathVariable Integer Codigo) {
		
		try {
			this.validarPerfil();
			if((usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2))) {
				
				  //funcionalidad de bitacora
				  bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				  this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipos de lugares", this.usuario.getOrganizacion().getCodigoPais()));
  
				tipoLugarService.deleteTipoLugarByCodigo(Codigo);
				return "redirect:/tipolugares";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}

	//modificar
	@GetMapping("/tipolugares/edit/{Codigo}")// envia el modelo a la pagina de editar usuario
	public String editTipoLugarForm (@PathVariable Integer Codigo,Model model) {
		
		try {
			this.validarPerfil();
			if((usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2))) {
				
				model.addAttribute("tipoLugar", tipoLugarService.getTipoLugarByCodigo(Codigo));
				return "tipoLugares/edit_tipoLugar";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
	}

	@PostMapping("/tipolugares/{Codigo}")// guarda el cambio y lo devuelve a la pagina usuarios con los datos nuevos
	public String updateTipoLugar (@PathVariable Integer Codigo ,@ModelAttribute TipoLugar tipoLugar, Model model) {

		this.validarPerfil();
		TipoLugar existingTipoLugar = tipoLugarService.getTipoLugarByCodigo(Codigo);
	    existingTipoLugar.setCI_Codigo(Codigo);
	    existingTipoLugar.setCVTitulo(tipoLugar.getCVTitulo());
	    existingTipoLugar.setCVDescripcion(tipoLugar.getCVDescripcion());
		tipoLugarService.updateTipoLugar(existingTipoLugar);
		//funcionalidad bitacora
		
		bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en tipos de lugares", this.usuario.getOrganizacion().getCodigoPais()));

		return "redirect:/tipolugares";
	}

}