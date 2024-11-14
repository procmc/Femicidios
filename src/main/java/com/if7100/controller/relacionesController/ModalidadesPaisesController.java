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
import com.if7100.entity.Modalidad;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.ModalidadesPaises;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.ModalidadService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.ModalidadesPaisesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ModalidadesPaisesController {
    
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
    private ModalidadService modalidadService;
    private ModalidadesPaisesService modalidadesPaisesService;

    public ModalidadesPaisesController(ModalidadService modalidadService,
    ModalidadesPaisesService modalidadesPaisesService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService) {

        this.modalidadService = modalidadService;
        this.modalidadesPaisesService = modalidadesPaisesService;
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

     @GetMapping("/modalidadesPaises/{id}")
    public String listmodalidadesPaises(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/modalidadesPaises/".concat(String.valueOf(id)).concat("/1");
    }

    @GetMapping("/modalidadesPaises/{id}/{pg}")
    public String listModalidadesPaises(Model model, @PathVariable Integer id, @PathVariable Integer pg) {
        this.validarPerfil();

        Modalidad modalidad = modalidadService.getModalidadById(id);

        // Obtener los procesos judiciales filtrados por el código de país
        List<ModalidadesPaises> modalidadesPaisesFiltrados = modalidadesPaisesService
                .getRelacionesByModalidad(modalidad);

        int numeroTotalElementos = modalidadesPaisesFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<ModalidadesPaises> modalidadesPaisesPaginados = modalidadesPaisesFiltrados
                .stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("modalidadesPaises", modalidadesPaisesPaginados);

        return "relacionesTemplates/modalidadesPaises/modalidadesPaises";
    }

  
    @GetMapping("/modalidadesPaises/new/{Id}")
    public String createMdalidadesPaises(Model model, @PathVariable Integer Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                Modalidad modalidad = new Modalidad();
                ModalidadesPaises modalidadesPaises = new ModalidadesPaises();

                modalidadesPaises.setModalidad(modalidad);
                modalidadesPaises.getModalidad().setCI_Codigo(Id);

                // Obtener los procesos judiciales filtrados por el código de país
                List<ModalidadesPaises> modalidadesPaisesFiltrados = modalidadesPaisesService
                        .getRelacionesByModalidad(modalidadesPaises.getModalidad());

                // Obtener los países seleccionados desde la base de datos por 
                List<String> paisesSeleccionados = modalidadesPaisesFiltrados.stream()
                        .map(igp -> igp.getPais().getISO2())
                        .collect(Collectors.toList());

                model.addAttribute("modalidadesPaises", modalidadesPaises);

                model.addAttribute("paises", paisesService.getAllPaises());
                model.addAttribute("modalidad", modalidadService.getAllModalidades());

                model.addAttribute("paisesSeleccionados", paisesSeleccionados); // Lista de países seleccionados

                return "relacionesTemplates/modalidadesPaises/create_modalidadesPaises";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {

            System.out.println("erro cre "+e);
            return "SinAcceso";
        }

    }

     @PostMapping("/modalidadesPaises")
    public String saveModalidadesPaises(@ModelAttribute ModalidadesPaises modalidadesPaises,
            @RequestParam List<String> paisesSeleccionados,
            Model model) {
        try {
            this.validarPerfil();

            for (String iso2 : paisesSeleccionados) {
                Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
                if (pais != null) {
                    ModalidadesPaises relacion = new ModalidadesPaises();
                    relacion.setModalidad(modalidadesPaises.getModalidad());
                    relacion.setPais(pais);
                    modalidadesPaisesService.saveModalidadesPaises(relacion);
                }
            }

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a modalidad",
                    this.usuario.getOrganizacion().getCodigoPais()));

            return "redirect:/modalidadesPaises/"
                    .concat(String.valueOf(modalidadesPaises.getModalidad().getCI_Codigo())).concat("/1");

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @GetMapping("/deletmodalidadesPaises/{id}/{modalidad}")
    public String deleteModalidadesPaises(@PathVariable Integer id, HttpSession session,
            @PathVariable Integer modalidad) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en modalidad / país",
                        this.usuario.getOrganizacion().getCodigoPais()));

                modalidadesPaisesService.deleteRelacionById(id);

                return "redirect:/modalidadesPaises/"
                .concat(String.valueOf(modalidad)).concat("/1");
        } else {
                return "SinAcceso";
            }
        } catch (Exception e) {

            return "SinAcceso";
        }
    }


}
