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
import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.TipoRelacionFamiliarPaises;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionFamiliarService;
import com.if7100.service.TipoRelacionService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.TipoRelacionFamiliarPaisesService;
import com.if7100.service.relacionesService.TipoRelacionPaisesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TipoRelacionFamiliarPaisesController {
    
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
    private TipoRelacionFamiliarService tipoRelacionFamiliarService;
    private TipoRelacionFamiliarPaisesService tipoRelacionFamiliarPaisesService;

    public TipoRelacionFamiliarPaisesController(TipoRelacionFamiliarService tipoRelacionFamiliarService,
    TipoRelacionFamiliarPaisesService tipoRelacionFamiliarPaisesService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService) {

        this.tipoRelacionFamiliarService = tipoRelacionFamiliarService;
        this.tipoRelacionFamiliarPaisesService = tipoRelacionFamiliarPaisesService;
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

        @GetMapping("/tipoRelacionFamiliarPaises/{id}")
    public String listtipoRelacionFamiliarPaises(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/tipoRelacionFamiliarPaises/".concat(String.valueOf(id)).concat("/1");
    }

     @GetMapping("/tipoRelacionFamiliarPaises/{id}/{pg}")
    public String listTipoRelacionFamiliarPaises(Model model, @PathVariable Integer id, @PathVariable Integer pg) {
        this.validarPerfil();

        TipoRelacionFamiliar tipoRelacionFamiliar = tipoRelacionFamiliarService.getTipoRelacionFamiliarById(id);

        // Obtener los procesos judiciales filtrados por el código de país
        List<TipoRelacionFamiliarPaises> tipoRelacionFamiliarPaisesFiltrados = tipoRelacionFamiliarPaisesService
                .getRelacionesByTipoRelacionFamiliar(tipoRelacionFamiliar);

        int numeroTotalElementos = tipoRelacionFamiliarPaisesFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<TipoRelacionFamiliarPaises> tipoRelacionFamiliarPaisesPaginados = tipoRelacionFamiliarPaisesFiltrados
                .stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tipoRelacionFamiliarPaises", tipoRelacionFamiliarPaisesPaginados);

        return "relacionesTemplates/tipoRelacionFamiliarPaises/tipoRelacionFamiliarPaises";
    }

    @GetMapping("/tipoRelacionFamiliarPaises/new/{Id}")
    public String createTipoRelacionFamiliarPaises(Model model, @PathVariable Integer Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                TipoRelacionFamiliar tipoRelacionFamiliar = new TipoRelacionFamiliar();
                TipoRelacionFamiliarPaises tipoRelacionFamiliarPaises = new TipoRelacionFamiliarPaises();

                tipoRelacionFamiliarPaises.setTipoRelacionFamiliar(tipoRelacionFamiliar);
                tipoRelacionFamiliarPaises.getTipoRelacionFamiliar().setCI_Codigo(Id);

                List<TipoRelacionFamiliarPaises> tipoRelacionFamiliarPaisesFiltrados = tipoRelacionFamiliarPaisesService
                        .getRelacionesByTipoRelacionFamiliar(tipoRelacionFamiliarPaises.getTipoRelacionFamiliar());

                // Obtener los países seleccionados desde la base de datos por 
                List<String> paisesSeleccionados = tipoRelacionFamiliarPaisesFiltrados.stream()
                        .map(igp -> igp.getPais().getISO2())
                        .collect(Collectors.toList());

                model.addAttribute("tipoRelacionFamiliarPaises", tipoRelacionFamiliarPaises);

                model.addAttribute("paises", paisesService.getAllPaises());
                model.addAttribute("tipoRelacionFamiliar", tipoRelacionFamiliarService.getAllTipoRelacionFamiliar());

                model.addAttribute("paisesSeleccionados", paisesSeleccionados); // Lista de países seleccionados

                return "relacionesTemplates/tipoRelacionFamiliarPaises/create_tipoRelacionFamiliarPaises"; 
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {

            return "SinAcceso";
        }

    }

    @PostMapping("/tipoRelacionFamiliarPaises")
    public String saveTipoRelacionFamiliarPaises(@ModelAttribute TipoRelacionFamiliarPaises tipoRelacionFamiliarPaises,
            @RequestParam List<String> paisesSeleccionados,
            Model model) {
        try {
            this.validarPerfil();

            for (String iso2 : paisesSeleccionados) {
                Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
                if (pais != null) {
                    TipoRelacionFamiliarPaises relacion = new TipoRelacionFamiliarPaises();
                    relacion.setTipoRelacionFamiliar(tipoRelacionFamiliarPaises.getTipoRelacionFamiliar());
                    relacion.setPais(pais);
                    tipoRelacionFamiliarPaisesService.saveTipoRelacionFamiliarPaises(relacion);
                }
            }

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a tipo de relacion familiar",
                    this.usuario.getOrganizacion().getCodigoPais()));

            return "redirect:/tipoRelacionFamiliarPaises/"
                    .concat(String.valueOf(tipoRelacionFamiliarPaises.getTipoRelacionFamiliar().getCI_Codigo())).concat("/1");

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

     @GetMapping("/delettipoRelacionFamiliarPaises/{id}/{tipoRelacionFamiliar}")
    public String deleteNivelEducativoPaises(@PathVariable Integer id, HttpSession session,
            @PathVariable Integer tipoRelacionFamiliar) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipo de relacion familiar / país",
                        this.usuario.getOrganizacion().getCodigoPais()));

                tipoRelacionFamiliarPaisesService.deleteRelacionById(id);

                return "redirect:/tipoRelacionFamiliarPaises/"
                .concat(String.valueOf(tipoRelacionFamiliar)).concat("/1");
        } else {
                return "SinAcceso";
            }
        } catch (Exception e) {

            return "SinAcceso";
        }
    }



}
