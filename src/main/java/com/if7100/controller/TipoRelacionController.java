package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.entity.Hecho;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionService;
import com.if7100.service.UsuarioPerfilService;

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
    private UsuarioPerfilService usuarioPerfilService;
    // instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    private PaisesService paisesService;

    // instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    private Paises paises;

    public TipoRelacionController(BitacoraService bitacoraService,
            TipoRelacionService tipoRelacionService, PerfilService perfilService, UsuarioRepository usuarioRepository,
            PaisesService paisesService,
            UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository) {
        super();
        this.tipoRelacionService = tipoRelacionService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
        this.paisesService = paisesService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.usuarioPerfilRepository = usuarioPerfilRepository;

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

    @GetMapping("/tiporelaciones")
    public String listTipoRelaciones(Model model) {
        this.validarPerfil();
        return "redirect:/tiporelacion/1";
    }

    @GetMapping("/tiporelacion/{pg}")
    public String listTipoRelacion(Model model, @PathVariable Integer pg) {
        this.validarPerfil();
        if (pg < 1) {
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
    public String createTipoRelacionForm(Model model) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                TipoRelacion tipoRelacion = new TipoRelacion();
                model.addAttribute("tipoRelacion", tipoRelacion);
                model.addAttribute("paises", paisesService.getAllPaises());

                return "tipoRelaciones/create_tipoRelacion";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/tiporelaciones")
    public String saveTipoRelacion(@ModelAttribute TipoRelacion tipoRelacion) {
        this.validarPerfil();
        tipoRelacionService.saveTipoRelacion(tipoRelacion);

        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en tipos de relaciones", this.usuario.getOrganizacion().getCodigoPais()));

        return "redirect:/tiporelaciones";
    }

    @GetMapping("/tiporelaciones/{id}")
    public String deleteTipoRelaciones(@PathVariable Integer id) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipos de relaciones", this.usuario.getOrganizacion().getCodigoPais()));

                tipoRelacionService.deleteTipoRelacionById(id);
                return "redirect:/tiporelaciones";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/tiporelaciones/edit/{id}")
    public String editTipoRelacionForm(@PathVariable Integer id, Model model) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                model.addAttribute("tipoRelacion", tipoRelacionService.getTipoRelacionById(id));
                model.addAttribute("paises", paisesService.getAllPaises());

                return "tipoRelaciones/edit_tipoRelacion";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/tiporelaciones/{id}")
    public String updateTipoRelacion(@PathVariable Integer id, @ModelAttribute TipoRelacion tipoRelacion, Model model) {
        this.validarPerfil();

        TipoRelacion existingTipoRelacion = tipoRelacionService.getTipoRelacionById(id);

        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en tipos de relaciones", this.usuario.getOrganizacion().getCodigoPais()));

        model.addAttribute("paises", paisesService.getAllPaises());

        existingTipoRelacion.setCI_Codigo(id);
        existingTipoRelacion.setCVTitulo(tipoRelacion.getCVTitulo());
        existingTipoRelacion.setCVDescripcion(tipoRelacion.getCVDescripcion());
        existingTipoRelacion.setCVPais(tipoRelacion.getCVPais());

        tipoRelacionService.updateTipoRelacion(existingTipoRelacion);
        return "redirect:/tiporelaciones";
    }
}
