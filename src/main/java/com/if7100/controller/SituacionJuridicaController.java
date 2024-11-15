package com.if7100.controller;

import com.if7100.entity.*;
import com.if7100.entity.relacionesEntity.NivelEducativoPaises;
import com.if7100.entity.relacionesEntity.SituacionJuridicaPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.SituacionJuridicaService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.SituacionJuridicaPaisesService;

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
    private UsuarioPerfilService usuarioPerfilService;

    private UsuarioRepository usuarioRepository;
    private UsuarioPerfilRepository usuarioPerfilRepository;

    private PerfilService perfilService;

    private BitacoraService bitacoraService;

    private Usuario usuario;

    private Perfil perfil;
    private PaisesService paisesService;
    private SituacionJuridicaPaisesService situacionJuridicaPaisesService;

    public SituacionJuridicaController(SituacionJuridicaService situacionJuridicaService,
            UsuarioRepository usuarioRepository, PerfilService perfilService, BitacoraService bitacoraService,
            UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository,
            PaisesService paisesService, SituacionJuridicaPaisesService situacionJuridicaPaisesService) {
        super();
        this.situacionJuridicaService = situacionJuridicaService;
        this.usuarioRepository = usuarioRepository;
        this.perfilService = perfilService;
        this.bitacoraService = bitacoraService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.paisesService = paisesService;
        this.situacionJuridicaPaisesService = situacionJuridicaPaisesService;
    }

    private void validarPerfil() {

        try {
            Usuario usuario = new Usuario();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));

            List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilRepository.findByUsuario(this.usuario);

            this.perfil = new Perfil(
                    perfilService.getPerfilById(usuarioPerfiles.get(0).getPerfil().getCI_Id()));

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos) {
        int numeroPagina = pg - 1;
        if (numeroTotalElementos < 10) {
            paginasDeseadas = 1;
        }
        if (numeroTotalElementos < 1) {
            numeroTotalElementos = 1;
        }
        int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
        return PageRequest.of(numeroPagina, tamanoPagina);
    }

    @GetMapping("/situacionesjuridicas")
    private String listSituacionesJuridicas(Model model) {
        this.validarPerfil();
        return "redirect:/situacionjuridica/1";
    }

    @GetMapping("/situacionjuridica/{pg}")
    public String listSituacionJuridica(Model model, @PathVariable Integer pg) {
        this.validarPerfil();
        if (pg < 1) {
            return "redirect:/situacionjuridica/1";
        }

        Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

        // obtiene el id del pais del hecho segun el pais de la organizacion del usuario
        List<SituacionJuridica> situacionJuridicaFiltrados = situacionJuridicaService
                .getSituacionJuridicaByCodigoPais(codigoPaisUsuario);

        int numeroTotalElementos = situacionJuridicaFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<SituacionJuridica> situacionJuridicaPaginados = situacionJuridicaFiltrados.stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("situacionesJuridicas", situacionJuridicaPaginados);
        return "situacionJuridica/situacionJuridica";
    }

    @GetMapping("situacionesjuridicas/new")
    public String createSituacionJuridica(Model model) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                SituacionJuridica situacionJuridica = new SituacionJuridica();
                model.addAttribute("situacionJuridica", situacionJuridica);
                model.addAttribute("listaPaises", paisesService.getAllPaises());

                return "situacionJuridica/create_situacionJuridica";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }

    }

    @PostMapping("/situacionesjuridicas")
    public String saveSituacionJuridica(@ModelAttribute SituacionJuridica situacionJuridica,
            @RequestParam List<String> paisesSeleccionados) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                situacionJuridicaService.saveSituacionJuridica(situacionJuridica);

                for (String iso2 : paisesSeleccionados) {
                    Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
                    if (pais != null) {
                        SituacionJuridicaPaises relacion = new SituacionJuridicaPaises();
                        relacion.setSituacionJuridica(situacionJuridica);
                        relacion.setPais(pais);
                        situacionJuridicaPaisesService.saveSituacionJuridicaPaises(relacion);
                    }
                }

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en situacion juridica",
                        this.usuario.getOrganizacion().getCodigoPais()));

                return "redirect:/situacionesjuridicas";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }

    }

    @GetMapping("/situacionesjuridicas/{id}")
    public String deleteSituacionesJuridicas(@PathVariable Integer id) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                situacionJuridicaService.deleteSituacionJuridicaById(id);

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en situacion juridica",
                        this.usuario.getOrganizacion().getCodigoPais()));

                return "redirect:/situacionesjuridicas";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/situacionesjuridicas/edit/{id}")
    public String editSituacionJuridicaForm(@PathVariable Integer id, Model model) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                model.addAttribute("situacionJuridica", situacionJuridicaService.getSituacionJuridicaById(id));
                return "situacionJuridica/edit_situacionJuridica";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/situacionesjuridicas/{id}")
    public String updateSituacionJuridica(@PathVariable Integer id,
            @ModelAttribute SituacionJuridica situacionJuridica) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                SituacionJuridica existingSituacionJuridica = situacionJuridicaService.getSituacionJuridicaById(id);
                existingSituacionJuridica.setCI_Codigo(id);
                existingSituacionJuridica.setCVTitulo(situacionJuridica.getCVTitulo());
                existingSituacionJuridica.setCVDescripcion(situacionJuridica.getCVDescripcion());
                situacionJuridicaService.updateSituacionJuridica(existingSituacionJuridica);

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en situacion juridica",
                        this.usuario.getOrganizacion().getCodigoPais()));

                return "redirect:/situacionesjuridicas";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }

    }

}
