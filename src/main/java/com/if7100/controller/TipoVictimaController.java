package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.service.BitacoraService;

import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoVictimaService;

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
public class TipoVictimaController {

    private TipoVictimaService tipoVictimaService;
  //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
  //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;


    public TipoVictimaController(BitacoraService bitacoraService,
TipoVictimaService tipoVictimaService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
        super();
        this.tipoVictimaService = tipoVictimaService;
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

    @GetMapping("/tipovictimas")
    public String listTipoVictimas(Model model){
        return "redirect:/tipovictima/1";
    }

    @GetMapping("/tipovictima/{pg}")
    public String listTipoVictima(Model model, @PathVariable Integer pg){
        if (pg < 1){
            return "redirect:/tipovictima/1";
        }

        int numeroTotalElementos = tipoVictimaService.getAllTipoVictimas().size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        Page<TipoVictima> tipoVictimasPage = tipoVictimaService.getAllTipoVictimasPage(pageable);

        List<Integer> nPaginas = IntStream.rangeClosed(1, tipoVictimasPage.getTotalPages())
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tipoVictimas", tipoVictimasPage.getContent());
        return "tipoVictimas/tipoVictimas";
    }

    @GetMapping("/tipovictimas/new")
    public String createTipoVictimaForm(Model model){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				TipoVictima tipoVictima = new TipoVictima();
		        model.addAttribute("tipoVictima", tipoVictima);
		        return "tipoVictimas/create_tipoVictima";
			}else {
				return "SinAcceso";
			}
			
		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @PostMapping("/tipovictimas")
    public String saveTipoVictima(@ModelAttribute TipoVictima tipoVictima){
        tipoVictimaService.saveTipoVictima(tipoVictima);
        
        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en tipos de victimas"));
		
        
        return "redirect:/tipovictimas";
    }

    @GetMapping("/tipovictimas/{id}")
    public String deleteTipoVictimas(@PathVariable Integer id){
    	
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
				bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipos de victimas"));
		
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
			if(!this.perfil.getCVRol().equals("Consulta")) {
				
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
        TipoVictima existingTipoVictima = tipoVictimaService.getTipoVictimaById(id);
        existingTipoVictima.setCI_Codigo(id);
        existingTipoVictima.setCVTitulo(tipoVictima.getCVTitulo());
        existingTipoVictima.setCVDescripcion(tipoVictima.getCVDescripcion());
        tipoVictimaService.updateTipoVictima(existingTipoVictima);
        
        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en tipos de victimas"));
		
        
        return "redirect:/tipovictimas";
    }

}
