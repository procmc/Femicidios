package com.if7100.controller.relacionesController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;
import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Lugar;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.IdentidadGeneroPais;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.IdentidadGeneroService;
import com.if7100.service.LugarService;
import com.if7100.service.PaisesService;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoLugarService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.IdentidadGeneroPaisService;

import io.micrometer.core.instrument.Meter.Id;

@Controller
public class IdentidadGeneroPaisesController {

    private LugarService lugarService;
    private UsuarioPerfilService usuarioPerfilService;
    private TipoLugarService tipoLugarService;


    private PaisesService paisesService;
    // instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    // instancias para control de bitacora
    private BitacoraService bitacoraService;
    private Usuario usuario;
    private IdentidadGeneroService identidadGeneroService;
    private IdentidadGeneroPaisService identidadGeneroPaisService;
    
    public IdentidadGeneroPaisesController(IdentidadGeneroService identidadGeneroService,
            IdentidadGeneroPaisService identidadGeneroPaisService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService) {
        this.identidadGeneroService = identidadGeneroService;
        this.identidadGeneroPaisService = identidadGeneroPaisService;
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

    @GetMapping("/identidadGeneroPais/{Id}")
    public String listIdentidadGeneroPais(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/identidadGeneroPais/".concat(String.valueOf(id)).concat("/1");
    }

    // crear paises
    @GetMapping("/identidadGeneroPais/new/{Id}")
    public String createIdentidadGeneroPaisForm(Model model, @PathVariable Integer Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                // Obtener lista de países y enviarla al modelo
                IdentidadGeneroPais identidadGeneroPais = new IdentidadGeneroPais();
                identidadGeneroPais.getIdentidadGenero().setId(Id);

                model.addAttribute("identidadGeneroPais", identidadGeneroPais);

                model.addAttribute("paises", paisesService.getAllPaises());
                model.addAttribute("identidadGenero", identidadGeneroService.getAllIdentidadGenero());

                return "relacionesTemplates/identidadGeneroPais/create_identidadGeneroPais";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            System.out.println("Errorsa: " + e);

            return "SinAcceso";
        }

    }

    @PostMapping("/identidadGeneroPais")
    public String saveHecho(@ModelAttribute IdentidadGeneroPais identidadGeneroPais, Model model) {
        try {
            this.validarPerfil();
            // guarda codigo pais internamente

            identidadGeneroPaisService.saveIdentidadGeneroPais(identidadGeneroPais);

            bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                    this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a identidad de genero",
                    this.usuario.getOrganizacion().getCodigoPais()));

            return "redirect:/relacionesTemplates/identidadGeneroPais/"
                    .concat(String.valueOf(identidadGeneroPais.getId()));
        } catch (Exception e) {
            return "SinAcceso";
        }
    }

}
