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
import com.if7100.entity.TipoVictima;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.TipoVictimaPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoVictimaService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.TipoVictimaPaisesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TipoVictimaPaisesController {
    
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
    private TipoVictimaService tipoVictimaService;
    private TipoVictimaPaisesService tipoVictimaPaisesService;

    public TipoVictimaPaisesController(TipoVictimaService tipoVictimaService,
    TipoVictimaPaisesService tipoVictimaPaisesService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService) {

        this.tipoVictimaService = tipoVictimaService;
        this.tipoVictimaPaisesService = tipoVictimaPaisesService;
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

     @GetMapping("/tipoVictimaPaises/{id}")
    public String listtipoVictimaPaises(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/tipoVictimaPaises/".concat(String.valueOf(id)).concat("/1");
    }

     @GetMapping("/tipoVictimaPaises/{id}/{pg}")
    public String listTipoVictimaPaises(Model model, @PathVariable Integer id, @PathVariable Integer pg) {
        this.validarPerfil();

        TipoVictima tipoVictima = tipoVictimaService.getTipoVictimaById(id);

        // Obtener los procesos judiciales filtrados por el código de país
        List<TipoVictimaPaises> tipoVictimaPaisesFiltrados = tipoVictimaPaisesService
                .getRelacionesByTipoVictima(tipoVictima);

        int numeroTotalElementos = tipoVictimaPaisesFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<TipoVictimaPaises> tipoVictimaPaisesPaginados = tipoVictimaPaisesFiltrados
                .stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tipoVictimaPaises", tipoVictimaPaisesPaginados);

        return "relacionesTemplates/tipoVictimaPaises/tipoVictimaPaises";
    }

    @GetMapping("/tipoVictimaPaises/new/{Id}")
    public String createTipoVictimaPaises(Model model, @PathVariable Integer Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                TipoVictima tipoVictima = new TipoVictima();
                TipoVictimaPaises tipoVictimaPaises = new TipoVictimaPaises();

                tipoVictimaPaises.setTipoVictima(tipoVictima);
                tipoVictimaPaises.getTipoVictima().setCI_Codigo(Id);

                // Obtener los procesos judiciales filtrados por el código de país
                List<TipoVictimaPaises> tipoVictimaPaisesFiltrados = tipoVictimaPaisesService
                        .getRelacionesByTipoVictima(tipoVictimaPaises.getTipoVictima());

                // Obtener los países seleccionados desde la base de datos por 
                List<String> paisesSeleccionados = tipoVictimaPaisesFiltrados.stream()
                        .map(igp -> igp.getPais().getISO2())
                        .collect(Collectors.toList());

                model.addAttribute("tipoVictimaPaises", tipoVictimaPaises);

                model.addAttribute("paises", paisesService.getAllPaises());
                model.addAttribute("tipoVictima", tipoVictimaService.getAllTipoVictimas());

                model.addAttribute("paisesSeleccionados", paisesSeleccionados); // Lista de países seleccionados

                return "relacionesTemplates/tipoVictimaPaises/create_tipoVictimaPaises"; 
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {

            return "SinAcceso";
        }

    }

     @PostMapping("/tipoVictimaPaises")
    public String saveTipoVictimaPaises(@ModelAttribute TipoVictimaPaises tipoVictimaPaises,
            @RequestParam List<String> paisesSeleccionados,
            Model model) {
        try {
            this.validarPerfil();

            for (String iso2 : paisesSeleccionados) {
                Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
                if (pais != null) {
                    TipoVictimaPaises relacion = new TipoVictimaPaises();
                    relacion.setTipoVictima(tipoVictimaPaises.getTipoVictima());
                    relacion.setPais(pais);
                    tipoVictimaPaisesService.saveTipoVictimaPaises(relacion);
                }
            }

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a tipo de victima",
                    this.usuario.getOrganizacion().getCodigoPais()));

            return "redirect:/tipoVictimaPaises/"
                    .concat(String.valueOf(tipoVictimaPaises.getTipoVictima().getCI_Codigo())).concat("/1");

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

     @GetMapping("/delettipoVictimaPaises/{id}/{tipoVictima}")
    public String deleteTipoVictimaPaises(@PathVariable Integer id, HttpSession session,
            @PathVariable Integer tipoVictima) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en tipo de relacion / país",
                        this.usuario.getOrganizacion().getCodigoPais()));

                tipoVictimaPaisesService.deleteRelacionById(id);

                return "redirect:/tipoVictimaPaises/"
                .concat(String.valueOf(tipoVictima)).concat("/1");
        } else {
                return "SinAcceso";
            }
        } catch (Exception e) {

            return "SinAcceso";
        }
    }

}
