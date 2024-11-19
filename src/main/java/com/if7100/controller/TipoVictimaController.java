package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.entity.relacionesEntity.TipoVictimaPaises;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoVictimaService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.TipoVictimaPaisesService;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TipoVictimaController {

    private TipoVictimaService tipoVictimaService;
    private UsuarioPerfilService usuarioPerfilService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    private PaisesService paisesService;
    private TipoVictimaPaisesService tipoVictimaPaisesService;

    public TipoVictimaController(BitacoraService bitacoraService,
TipoVictimaService tipoVictimaService, PerfilService perfilService, UsuarioRepository usuarioRepository,
UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository, PaisesService paisesService,
TipoVictimaPaisesService tipoVictimaPaisesService) {
        super();
        this.tipoVictimaService = tipoVictimaService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService= bitacoraService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.paisesService = paisesService;
        this.tipoVictimaPaisesService = tipoVictimaPaisesService;
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

    @GetMapping("/tipovictimas")
    public String listTipoVictimas(Model model){
        this.validarPerfil();

        return "redirect:/tipovictima/1";
    }

    @GetMapping("/tipovictima/{pg}")
    public String listTipoVictima(Model model, @PathVariable Integer pg){
        this.validarPerfil();

        if (pg < 1){
            return "redirect:/tipovictima/1";
        }

         Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		List<TipoVictima> tipoVictimaFiltrados = tipoVictimaService
				.getTipoVictimaByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = tipoVictimaFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<TipoVictima> tipoVictimaPaginados = tipoVictimaFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tipoVictimas", tipoVictimaPaginados);
        return "tipoVictimas/tipoVictimas";
    }

    @GetMapping("/tipovictimas/new")
    public String createTipoVictimaForm(Model model){
    	
    	try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				
				TipoVictima tipoVictima = new TipoVictima();
		        model.addAttribute("tipoVictima", tipoVictima);
                model.addAttribute("listaPaises", paisesService.getAllPaises());

		        return "tipoVictimas/create_tipoVictima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/tipovictimas")
    public String saveTipoVictima(@ModelAttribute TipoVictima tipoVictima, @RequestParam List<String> paisesSeleccionados){
        this.validarPerfil();

        tipoVictimaService.saveTipoVictima(tipoVictima);
        
        for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				TipoVictimaPaises relacion = new TipoVictimaPaises();
				relacion.setTipoVictima(tipoVictima);
				relacion.setPais(pais);
				tipoVictimaPaisesService.saveTipoVictimaPaises(relacion);
			}
		}

        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en tipos de victimas", this.usuario.getOrganizacion().getCodigoPais()));
		
        
        return "redirect:/tipovictimas";
    }

    @GetMapping("/tipovictimas/{id}")
    public String deleteTipoVictimas(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipos de victimas", this.usuario.getOrganizacion().getCodigoPais()));
		
				tipoVictimaService.deleteTipoVictimaById(id);
		        return "redirect:/tipovictimas";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/tipovictimas/edit/{id}")
    public String editTipoVictimaForm(@PathVariable Integer id, Model model){
    	
    	try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				
				model.addAttribute("tipoVictima", tipoVictimaService.getTipoVictimaById(id));
		        return "tipoVictimas/edit_tipoVictima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/tipovictimas/{id}")
    public String updateTipoVictima(@PathVariable Integer id, @ModelAttribute TipoVictima tipoVictima){
        this.validarPerfil();

        TipoVictima existingTipoVictima = tipoVictimaService.getTipoVictimaById(id);
        existingTipoVictima.setCI_Codigo(id);
        existingTipoVictima.setCVTitulo(tipoVictima.getCVTitulo());
        existingTipoVictima.setCVDescripcion(tipoVictima.getCVDescripcion());
        tipoVictimaService.updateTipoVictima(existingTipoVictima);
        
        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en tipos de victimas", this.usuario.getOrganizacion().getCodigoPais()));
		
        
        return "redirect:/tipovictimas";
    }

}
