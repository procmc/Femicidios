package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;
import com.if7100.service.SituacionJuridicaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SituacionJuridicaController {

    private SituacionJuridicaService situacionJuridicaService;

    private UsuarioRepository usuarioRepository;

    private PerfilService perfilService;

    private BitacoraService bitacoraService;

    private Usuario usuario;

    private Perfil perfil;

    public SituacionJuridicaController(SituacionJuridicaService situacionJuridicaService, UsuarioRepository usuarioRepository, PerfilService perfilService, BitacoraService bitacoraService) {
        super();
        this.situacionJuridicaService = situacionJuridicaService;
        this.usuarioRepository = usuarioRepository;
        this.perfilService = perfilService;
        this.bitacoraService = bitacoraService;
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

    @GetMapping("/situacionesjuridicas")
    public String listSituacionesJuridicas(Model model){
        model.addAttribute("situacionesJuridicas", situacionJuridicaService.getAllSituacionJuridica());
        return "situacionJuridica/situacionesJuridicas";
    }

    @GetMapping("situacionesjuridicas/new")
    public String createSituacionJuridica(Model model){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {

                SituacionJuridica situacionJuridica = new SituacionJuridica();
                model.addAttribute("situacionJuridica", situacionJuridica);
                return "situacionJuridica/create_situacionJuridica";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }

    }

    @PostMapping("/situacionesjuridicas")
    public String saveSituacionJuridica(@ModelAttribute("situacionJuridica") SituacionJuridica situacionJuridica){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                situacionJuridicaService.saveSituacionJuridica(situacionJuridica);
                String descripcion = "Creo una situacion juridica";
                Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
                bitacoraService.saveBitacora(bitacora);
                return "redirect:/situacionesjuridicas";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }

    }

    @GetMapping("/situacionesjuridicas/{id}")
    public String deleteSituacionesJuridicas(@PathVariable Integer id){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                situacionJuridicaService.deleteSituacionJuridicaById(id);
                String descripcion = "Elimino una situacion juridica";
                Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
                bitacoraService.saveBitacora(bitacora);
                return "redirect:/situacionesjuridicas";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/situacionesjuridicas/edit/{id}")
    public String editSituacionJuridicaForm(@PathVariable Integer id, Model model){
        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("situacionJuridica", situacionJuridicaService.getSituacionJuridicaById(id));
                return "situacionJuridica/edit_situacionJuridica";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/situacionesjuridicas/{id}")
    public String updateSituacionJuridica(@PathVariable Integer id, @ModelAttribute("situacionJuridica") SituacionJuridica situacionJuridica){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                SituacionJuridica existingSituacionJuridica = situacionJuridicaService.getSituacionJuridicaById(id);
                existingSituacionJuridica.setCI_Codigo(id);
                existingSituacionJuridica.setCVTitulo(situacionJuridica.getCVTitulo());
                existingSituacionJuridica.setCVDescripcion(situacionJuridica.getCVDescripcion());
                situacionJuridicaService.updateSituacionJuridica(existingSituacionJuridica);
                String descripcion = "Actualizo una situacion juridica";
                Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(), descripcion, this.perfil.getCVRol());
                bitacoraService.saveBitacora(bitacora);
                return "redirect:/situacionesjuridicas";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }

    }


}
