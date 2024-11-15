package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.service.BitacoraService;
import com.if7100.service.PaisesService;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.PerfilService;
import com.if7100.service.TipoRelacionService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.TipoRelacionPaisesService;

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

import java.util.List;
import java.util.stream.Collectors;
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
    private TipoRelacionPaisesService tipoRelacionPaisesService;

    public TipoRelacionController(BitacoraService bitacoraService,
            TipoRelacionService tipoRelacionService, PerfilService perfilService, UsuarioRepository usuarioRepository,
            PaisesService paisesService,
            UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository,
            TipoRelacionPaisesService tipoRelacionPaisesService) {
        super();
        this.tipoRelacionService = tipoRelacionService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
        this.paisesService = paisesService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.tipoRelacionPaisesService = tipoRelacionPaisesService;

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

        Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		// obtiene el id del pais del hecho segun el pais de la organizacion del usuario
		List<TipoRelacion> tipoRelacionFiltrados = tipoRelacionService
				.getTipoRelacionByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = tipoRelacionFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<TipoRelacion> tipoRelacionPaginados = tipoRelacionFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("tiporelaciones", tipoRelacionPaginados);
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
				model.addAttribute("listaPaises", paisesService.getAllPaises());

                return "tipoRelaciones/create_tipoRelacion";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/tiporelaciones")
    public String saveTipoRelacion(@ModelAttribute TipoRelacion tipoRelacion, @RequestParam List<String> paisesSeleccionados) {
        this.validarPerfil();
        tipoRelacionService.saveTipoRelacion(tipoRelacion);

        for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				TipoRelacionPaises relacion = new TipoRelacionPaises();
				relacion.setTipoRelacion(tipoRelacion);
				relacion.setPais(pais);
				tipoRelacionPaisesService.saveTipoRelacionPaises(relacion);
			}
		}

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

        existingTipoRelacion.setCI_Codigo(id);
        existingTipoRelacion.setCVTitulo(tipoRelacion.getCVTitulo());
        existingTipoRelacion.setCVDescripcion(tipoRelacion.getCVDescripcion());

        tipoRelacionService.updateTipoRelacion(existingTipoRelacion);
        return "redirect:/tiporelaciones";
    }
}
