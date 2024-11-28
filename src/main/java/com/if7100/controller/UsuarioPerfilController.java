package com.if7100.controller;

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
import com.if7100.service.UsuarioService;
import com.if7100.service.relacionesService.TipoRelacionPaisesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioPerfilController {
    
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

    private UsuarioService usuarioService;

    // instancias para control de relaciones
    private TipoRelacionService tipoRelacionService;
    private TipoRelacionPaisesService tipoRelacionPaisesService;

    public UsuarioPerfilController(TipoRelacionService tipoRelacionService,
    TipoRelacionPaisesService tipoRelacionPaisesService,
            UsuarioRepository usuarioRepository, UsuarioPerfilRepository usuarioPerfilRepository,
            PerfilService perfilService,
            BitacoraService bitacoraService, UsuarioPerfilService usuarioPerfilService,
            PaisesService paisesService, UsuarioService usuarioService) {

        this.tipoRelacionService = tipoRelacionService;
        this.tipoRelacionPaisesService = tipoRelacionPaisesService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.perfilService = perfilService;
        this.bitacoraService = bitacoraService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.paisesService = paisesService;
        this.usuarioService = usuarioService;
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

      @GetMapping("/usuarioPerfil/{id}")
    public String listusuarioPerfil(Model model, @PathVariable Integer id) {
        this.validarPerfil();
        return "redirect:/usuarioPerfil/".concat(String.valueOf(id)).concat("/1");
    }

      @GetMapping("/usuarioPerfil/{id}/{pg}")
    public String listUsuarioPerfil(Model model, @PathVariable String id, @PathVariable Integer pg) {
        this.validarPerfil();

        Usuario usuario = usuarioService.getUsuarioByCVCedula(id);

        List<UsuarioPerfil> usuarioPerfilFiltrados = usuarioPerfilService
                .getUsuarioPerfilByUsuario(usuario);

        int numeroTotalElementos = usuarioPerfilFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<UsuarioPerfil> usuarioPerfilPaginados = usuarioPerfilFiltrados
                .stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("usuarioPerfil", usuarioPerfilPaginados);
 
        return "usuarioPerfil/usuarioPerfil";
    }

  
    @GetMapping("/usuarioPerfil/new/{Id}")
    public String createUsuarioPerfil(Model model, @PathVariable String Id) {

        try {
            this.validarPerfil();

            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                        
                Usuario usuario = usuarioService.getUsuarioByCVCedula(Id);
                UsuarioPerfil usuarioPerfil = new UsuarioPerfil();

                usuarioPerfil.setUsuario(usuario);


                // Obtener los procesos judiciales filtrados por el código de país
                List<UsuarioPerfil> usuarioPerfilFiltrados = usuarioPerfilService
                        .getUsuarioPerfilByUsuario(usuarioPerfil.getUsuario());

                // Obtener los países seleccionados desde la base de datos por 
                List<Integer> perfilesSeleccionados = usuarioPerfilFiltrados.stream()
                        .map(igp -> igp.getPerfil().getCI_Id())
                        .collect(Collectors.toList());

                model.addAttribute("usuarioPerfil", usuarioPerfil);
                model.addAttribute("usuario", usuarioService.getAllUsuarios());
                model.addAttribute("perfiles", perfilService.getAllPerfiles());
                model.addAttribute("perfilesSeleccionados", perfilesSeleccionados); // Lista de países seleccionados

                return "usuarioPerfil/create_usuarioPerfil"; 
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            System.out.println("erssasd "+e);
            return "SinAcceso";
        }

    }

  
@PostMapping("/usuarioPerfil")
public String saveusuarioPerfil(@ModelAttribute UsuarioPerfil usuarioPerfil,
        @RequestParam List<Integer> perfilesSeleccionados,
        Model model) {
    try {
        this.validarPerfil();

        for (Integer idPerfil : perfilesSeleccionados) {
			Perfil perfil = perfilService.getPerfilById(idPerfil); 
			if (perfil != null) {
				UsuarioPerfil relacion = new UsuarioPerfil();
                System.out.println("relacionUsuario "+usuarioPerfil.getUsuario().getCVCedula());
				relacion.setUsuario(usuarioService.getUsuarioByCVCedula(usuarioPerfil.getUsuario().getCVCedula()));
				relacion.setPerfil(perfil);
				usuarioPerfilService.saveUsuarioPerfil(relacion);
			}
		}

        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                this.usuario.getCVNombre(), this.perfil.getCVRol(), "Agrega país a tipo de relacion",
                this.usuario.getOrganizacion().getCodigoPais()));

        return "redirect:/usuarioPerfil/"
                .concat(String.valueOf(usuarioPerfil.getUsuario().getCVCedula())).concat("/1");

    } catch (Exception e) {
        System.out.println("ersd "+e);
        return "SinAcceso";
    }
}










     @GetMapping("/deleteusuarioPerfil/eliminar/{id}/{usuario}")
    public String deleteUsuarioPerfil( HttpSession session,
            @PathVariable String usuario, @PathVariable Integer id) {
        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
                    || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina rol a usuario",
                        this.usuario.getOrganizacion().getCodigoPais()));

                usuarioPerfilService.deleteUsuarioPerfilById(id);

                return "redirect:/usuarioPerfil/"
                .concat(String.valueOf(usuario)).concat("/1");
        } else {
                return "SinAcceso";
            }
        } catch (Exception e) {

            return "SinAcceso";
        }
    }

}
