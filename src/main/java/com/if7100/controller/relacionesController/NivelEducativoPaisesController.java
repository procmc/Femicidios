package com.if7100.controller.relacionesController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import com.if7100.entity.Bitacora;
import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.NivelEducativoPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.NivelEducativoService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.NivelEducativoPaisesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NivelEducativoPaisesController {
    
    private UsuarioPerfilService usuarioPerfilService;

    private PaisesService paisesService;
    // instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    // instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;

    // instancias para control de relaciones
    private NivelEducativoService nivelEducativoService;
    private NivelEducativoPaisesService nivelEducativoPaisesService;

    public NivelEducativoPaisesController(NivelEducativoService nivelEducativoService,
    NivelEducativoPaisesService nivelEducativoPaisesService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService) {

        this.nivelEducativoService = nivelEducativoService;
        this.nivelEducativoPaisesService = nivelEducativoPaisesService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.perfilService = perfilService;
        this.bitacoraService = bitacoraService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.paisesService = paisesService;
    }

    private void validarPerfil() {

        try {
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

         @GetMapping("/nivelEducativoPaises/{id}")
    public String listnivelEducativoPaises(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/nivelEducativoPaises/".concat(String.valueOf(id)).concat("/1");
    }

     @GetMapping("/nivelEducativoPaises/{id}/{pg}")
    public String listNivelEducativoPaises(Model model, @PathVariable Integer id, @PathVariable Integer pg) {
        this.validarPerfil();

        NivelEducativo nivelEducativo = nivelEducativoService.getNivelEducativoById(id);

        // Obtener los procesos judiciales filtrados por el código de país
        List<NivelEducativoPaises> nivelEducativoPaisesFiltrados = nivelEducativoPaisesService
                .getRelacionesByNivelEducativo(nivelEducativo);

        int numeroTotalElementos = nivelEducativoPaisesFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<NivelEducativoPaises> nivelEducativoPaisesPaginados = nivelEducativoPaisesFiltrados
                .stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("nivelEducativoPaises", nivelEducativoPaisesPaginados);

        return "relacionesTemplates/nivelEducativoPaises/nivelEducativoPaises";
    }

    @GetMapping("/nivelEducativoPaises/new/{Id}")
    public String createNivelEducativoPaises(Model model, @PathVariable Integer Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                NivelEducativo nivelEducativo = new NivelEducativo();
                NivelEducativoPaises nivelEducativoPaises = new NivelEducativoPaises();

                nivelEducativoPaises.setNivelEducativo(nivelEducativo);
                nivelEducativoPaises.getNivelEducativo().setCI_Id(Id);

                // Obtener los procesos judiciales filtrados por el código de país
                List<NivelEducativoPaises> nivelEducativoPaisesFiltrados = nivelEducativoPaisesService
                        .getRelacionesByNivelEducativo(nivelEducativoPaises.getNivelEducativo());

                // Obtener los países seleccionados desde la base de datos por 
                List<String> paisesSeleccionados = nivelEducativoPaisesFiltrados.stream()
                        .map(igp -> igp.getPais().getISO2())
                        .collect(Collectors.toList());

                model.addAttribute("nivelEducativoPaises", nivelEducativoPaises);

                model.addAttribute("paises", paisesService.getAllPaises());
                model.addAttribute("nivelEducativo", nivelEducativoService.getAllNivelEducativo());

                model.addAttribute("paisesSeleccionados", paisesSeleccionados); // Lista de países seleccionados

                return "relacionesTemplates/nivelEducativoPaises/create_nivelEducativoPaises";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {

            System.out.println("erro cre "+e);
            return "SinAcceso";
        }

    }

         @PostMapping("/nivelEducativoPaises")
    public String saveNivelEducativoPaises(@ModelAttribute NivelEducativoPaises nivelEducativoPaises,
            @RequestParam List<String> paisesSeleccionados,
            Model model) {
        try {
            this.validarPerfil();

            for (String iso2 : paisesSeleccionados) {
                Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
                if (pais != null) {
                    NivelEducativoPaises relacion = new NivelEducativoPaises();
                    relacion.setNivelEducativo(nivelEducativoPaises.getNivelEducativo());
                    relacion.setPais(pais);
                    nivelEducativoPaisesService.saveNivelEducativoPaises(relacion);
                }
            }

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a nivel educativo",
                    this.usuario.getOrganizacion().getCodigoPais()));

            return "redirect:/nivelEducativoPaises/"
                    .concat(String.valueOf(nivelEducativoPaises.getNivelEducativo().getCI_Id())).concat("/1");

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

        @GetMapping("/deletnivelEducativoPaises/{id}/{nivelEducativo}")
    public String deleteNivelEducativoPaises(@PathVariable Integer id, HttpSession session,
            @PathVariable Integer nivelEducativo) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en nivel educativo / país",
                        this.usuario.getOrganizacion().getCodigoPais()));

                nivelEducativoPaisesService.deleteRelacionById(id);

                return "redirect:/nivelEducativoPaises/"
                .concat(String.valueOf(nivelEducativo)).concat("/1");
        } else {
                return "SinAcceso";
            }
        } catch (Exception e) {

            return "SinAcceso";
        }
    }

}
