package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;
import com.if7100.service.SituacionJuridicaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/situacionesjuridicas")
    private String listSituacionesJuridicas(Model model){
        this.validarPerfil();
        return "redirect:/situacionjuridica/1";
    }

    @GetMapping("/situacionjuridica/{pg}")
    public String listSituacionJuridica(Model model, @PathVariable Integer pg){
        this.validarPerfil();
        if (pg < 1){
            return "redirect:/situacionjuridica/1";
        }

        int numeroTotalElementos = situacionJuridicaService.getAllSituacionJuridica().size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        Page<SituacionJuridica> situacionJuridicaPage = situacionJuridicaService.getAllSituacionJuridicaPage(pageable);

        List<Integer> nPaginas = IntStream.rangeClosed(1, situacionJuridicaPage.getTotalPages())
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("situacionesJuridicas", situacionJuridicaPage.getContent());
        return "situacionJuridica/situacionJuridica";
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
    public String saveSituacionJuridica(@ModelAttribute SituacionJuridica situacionJuridica){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                situacionJuridicaService.saveSituacionJuridica(situacionJuridica);
                
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en situacion juridica"));

                
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
                
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en situacion juridica"));

                
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
    public String updateSituacionJuridica(@PathVariable Integer id, @ModelAttribute SituacionJuridica situacionJuridica){

        try {
            this.validarPerfil();
            if(!this.perfil.getCVRol().equals("Consulta")) {
                SituacionJuridica existingSituacionJuridica = situacionJuridicaService.getSituacionJuridicaById(id);
                existingSituacionJuridica.setCI_Codigo(id);
                existingSituacionJuridica.setCVTitulo(situacionJuridica.getCVTitulo());
                existingSituacionJuridica.setCVDescripcion(situacionJuridica.getCVDescripcion());
                situacionJuridicaService.updateSituacionJuridica(existingSituacionJuridica);
                
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en situacion juridica"));
                
                return "redirect:/situacionesjuridicas";
            }else {
                return "SinAcceso";
            }

        }catch (Exception e) {
            return "SinAcceso";
        }

    }


}
