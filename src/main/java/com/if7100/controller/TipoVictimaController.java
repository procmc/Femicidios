package com.if7100.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.if7100.entity.Perfil;
import com.if7100.entity.TipoVictima;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoVictimaService;

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

    @GetMapping("/tipovictimas")
    public String listTipoVictimas(Model model){
        model.addAttribute("tipoVictimas", tipoVictimaService.getAllTipoVictimas());
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
    public String saveTipoVictima(@ModelAttribute("tipoVictima") TipoVictima tipoVictima){
        tipoVictimaService.saveTipoVictima(tipoVictima);
        return "redirect:/tipovictimas";
    }

    @GetMapping("/tipovictimas/{id}")
    public String deleteTipoVictimas(@PathVariable Integer id){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
				//Bitacora bitacora=new Bitacora(this.usuario.getCI_Id(),this.usuario.getCVNombre(),this.perfil.getCVRol());
				//bitacoraService.saveBitacora(bitacora);

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
    public String updateTipoVictima(@PathVariable Integer id, @ModelAttribute("tipoVictima") TipoVictima tipoVictima){
        TipoVictima existingTipoVictima = tipoVictimaService.getTipoVictimaById(id);
        existingTipoVictima.setCI_Codigo(id);
        existingTipoVictima.setCVTitulo(tipoVictima.getCVTitulo());
        existingTipoVictima.setCVDescripcion(tipoVictima.getCVDescripcion());
        tipoVictimaService.updateTipoVictima(existingTipoVictima);
        return "redirect:/tipovictimas";
    }

}
