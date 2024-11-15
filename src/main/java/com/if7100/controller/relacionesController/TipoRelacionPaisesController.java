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
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.TipoRelacionPaisesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TipoRelacionPaisesController {
    
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
    private TipoRelacionService tipoRelacionService;
    private TipoRelacionPaisesService tipoRelacionPaisesService;

    public TipoRelacionPaisesController(TipoRelacionService tipoRelacionService,
    TipoRelacionPaisesService tipoRelacionPaisesService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService) {

        this.tipoRelacionService = tipoRelacionService;
        this.tipoRelacionPaisesService = tipoRelacionPaisesService;
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

    @GetMapping("/tipoRelacionPaises/{id}")
    public String listtipoRelacionPaises(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/tipoRelacionPaises/".concat(String.valueOf(id)).concat("/1");
    }

     @GetMapping("/tipoRelacionPaises/{id}/{pg}")
    public String listTipoRelacionPaises(Model model, @PathVariable Integer id, @PathVariable Integer pg) {
        this.validarPerfil();

        TipoRelacion tipoRelacion = tipoRelacionService.getTipoRelacionById(id);

        // Obtener los procesos judiciales filtrados por el código de país
        List<TipoRelacionPaises> tipoRelacionPaisesFiltrados = tipoRelacionPaisesService
                .getRelacionesByTipoRelacion(tipoRelacion);

        int numeroTotalElementos = tipoRelacionPaisesFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<TipoRelacionPaises> tipoRelacionPaisesPaginados = tipoRelacionPaisesFiltrados
                .stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tipoRelacionPaises", tipoRelacionPaisesPaginados);

        return "relacionesTemplates/tipoRelacionPaises/tipoRelacionPaises";
    }

    @GetMapping("/tipoRelacionPaises/new/{Id}")
    public String createTipoRelacionPaises(Model model, @PathVariable Integer Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                TipoRelacion tipoRelacion = new TipoRelacion();
                TipoRelacionPaises tipoRelacionPaises = new TipoRelacionPaises();

                tipoRelacionPaises.setTipoRelacion(tipoRelacion);
                tipoRelacionPaises.getTipoRelacion().setCI_Codigo(Id);

                // Obtener los procesos judiciales filtrados por el código de país
                List<TipoRelacionPaises> tipoRelacionPaisesFiltrados = tipoRelacionPaisesService
                        .getRelacionesByTipoRelacion(tipoRelacionPaises.getTipoRelacion());

                // Obtener los países seleccionados desde la base de datos por 
                List<String> paisesSeleccionados = tipoRelacionPaisesFiltrados.stream()
                        .map(igp -> igp.getPais().getISO2())
                        .collect(Collectors.toList());

                model.addAttribute("tipoRelacionPaises", tipoRelacionPaises);

                model.addAttribute("paises", paisesService.getAllPaises());
                model.addAttribute("tipoRelacion", tipoRelacionService.getAllTipoRelaciones());

                model.addAttribute("paisesSeleccionados", paisesSeleccionados); // Lista de países seleccionados

                return "relacionesTemplates/tipoRelacionPaises/create_tipoRelacionPaises"; 
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {

            return "SinAcceso";
        }

    }

     @PostMapping("/tipoRelacionPaises")
    public String saveTipoRelacionPaises(@ModelAttribute TipoRelacionPaises tipoRelacionPaises,
            @RequestParam List<String> paisesSeleccionados,
            Model model) {
        try {
            this.validarPerfil();

            for (String iso2 : paisesSeleccionados) {
                Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
                if (pais != null) {
                    TipoRelacionPaises relacion = new TipoRelacionPaises();
                    relacion.setTipoRelacion(tipoRelacionPaises.getTipoRelacion());
                    relacion.setPais(pais);
                    tipoRelacionPaisesService.saveTipoRelacionPaises(relacion);
                }
            }

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a tipo de relacion",
                    this.usuario.getOrganizacion().getCodigoPais()));

            return "redirect:/tipoRelacionPaises/"
                    .concat(String.valueOf(tipoRelacionPaises.getTipoRelacion().getCI_Codigo())).concat("/1");

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/delettipoRelacionPaises/{id}/{tipoRelacion}")
    public String deleteNivelEducativoPaises(@PathVariable Integer id, HttpSession session,
            @PathVariable Integer tipoRelacion) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipo de relacion / país",
                        this.usuario.getOrganizacion().getCodigoPais()));

                tipoRelacionPaisesService.deleteRelacionById(id);

                return "redirect:/tipoRelacionPaises/"
                .concat(String.valueOf(tipoRelacion)).concat("/1");
        } else {
                return "SinAcceso";
            }
        } catch (Exception e) {

            return "SinAcceso";
        }
    }


}
