package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.Paises;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;

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

import com.if7100.entity.Hecho;
import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.NivelEducativoService;
import com.if7100.service.PerfilService;

import java.util.List;
import java.util.stream.IntStream;


@Controller
public class NivelEducativoController {
    private NivelEducativoService nivelEducativoService;
    //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    private PaisesService paisesService;
    //instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    private Paises paises;
    public NivelEducativoController(BitacoraService bitacoraService,
                                    NivelEducativoService nivelEducativoService, PerfilService perfilService, UsuarioRepository usuarioRepository,PaisesService paisesService) {

        super();
        this.nivelEducativoService = nivelEducativoService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
        this.paisesService = paisesService;


    }

    private void validarPerfil() {

        try {
            Usuario usuario = new Usuario();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));

            this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

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

    @GetMapping("/nivelEducativo")
    public String listNivelEducativo(Model model) {
        this.validarPerfil();
        return "redirect:/niveleducativo/1";
    }

    @GetMapping("/niveleducativo/{pg}")
    public String listNivelesEducativos(Model model, @PathVariable Integer pg) {
        this.validarPerfil();

        if (pg < 1) {
            return "redirect:/niveleducativo/1";
        }

        int numeroTotalElementos = nivelEducativoService.getAllNivelEducativo().size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        Page<NivelEducativo> nivelEducativoPage = nivelEducativoService.getAllNivelEducativoPage(pageable);

        List<Integer> nPaginas = IntStream.rangeClosed(1, nivelEducativoPage.getTotalPages())
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("nivelesEducativos", nivelEducativoPage.getContent());
        return "nivelEducativo/nivelEducativo";
    }

    @GetMapping("/nivelEducativo/new")
    public String createUsuarioForm(Model model) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                NivelEducativo nivelEducativo = new NivelEducativo();
                model.addAttribute("nivelEducativo", nivelEducativo);
                model.addAttribute("paises", paisesService.getAllPaises());
                return "nivelEducativo/create_nivelEducativo";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/nivelEducativo")
    public String saveNivelEducativo(@ModelAttribute NivelEducativo nivelEducativo) {
        this.validarPerfil();
        nivelEducativoService.saveNivelEducativo(nivelEducativo);
        
        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en nivel educativo"));

        
        return "redirect:/nivelEducativo";


    }

    @GetMapping("/nivelEducativo/{id}")
    public String deleteNivelEducativo(@PathVariable Integer id) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en nivel educativo"));

                nivelEducativoService.deleteNivelEducativoById(id);
                return "redirect:/nivelEducativo";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/nivelEducativo/edit/{id}")
    public String editNivelEducativoForm(@PathVariable Integer id, Model model) {

        try {
            this.validarPerfil();
            if (!this.perfil.getCVRol().equals("Consulta")) {

                model.addAttribute("nivelEducativo", nivelEducativoService.getNivelEducativoById(id));
            	model.addAttribute("paises", paisesService.getAllPaises());
                return "nivelEducativo/edit_nivelEduactivo";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/nivelEducativo/{id}")
    public String updateNivelEducativo(@PathVariable Integer id, @ModelAttribute NivelEducativo nivelEducativo, Model model) {
        this.validarPerfil();
        NivelEducativo existingNivelEducativo = nivelEducativoService.getNivelEducativoById(id);
        model.addAttribute("paises", paisesService.getAllPaises());
        existingNivelEducativo.setCI_Id(id);
        existingNivelEducativo.setCVTitulo(nivelEducativo.getCVTitulo());
        existingNivelEducativo.setCVDescripcion(nivelEducativo.getCVDescripcion());
        existingNivelEducativo.setCVPais(nivelEducativo.getCVPais());
        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
				this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en nivel educativo"));

        nivelEducativoService.updateNivelEducativo(existingNivelEducativo);
        return "redirect:/nivelEducativo";
    }


}
