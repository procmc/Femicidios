package com.if7100.controller;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.ModalidadesPaises;
import com.if7100.entity.relacionesEntity.NivelEducativoPaises;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Perfil;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.NivelEducativoService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;
import com.if7100.service.relacionesService.NivelEducativoPaisesService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class NivelEducativoController {
    private NivelEducativoService nivelEducativoService;
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
    private NivelEducativoPaisesService nivelEducativoPaisesService;
    public NivelEducativoController(BitacoraService bitacoraService,
            NivelEducativoService nivelEducativoService, PerfilService perfilService,
            UsuarioRepository usuarioRepository, PaisesService paisesService, UsuarioPerfilService usuarioPerfilService,
            UsuarioPerfilRepository usuarioPerfilRepository, NivelEducativoPaisesService nivelEducativoPaisesService) {

        super();
        this.nivelEducativoService = nivelEducativoService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
        this.bitacoraService = bitacoraService;
        this.paisesService = paisesService;
        this.usuarioPerfilService = usuarioPerfilService;
        this.usuarioPerfilRepository = usuarioPerfilRepository;
        this.nivelEducativoPaisesService = nivelEducativoPaisesService;

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

       Integer codigoPaisUsuario = this.usuario.getOrganizacion().getCodigoPais();

		// obtiene el id del pais del hecho segun el pais de la organizacion del usuario
		List<NivelEducativo> nivelEducativoFiltrados = nivelEducativoService
				.getNivelEducativoByCodigoPais(codigoPaisUsuario);

		int numeroTotalElementos = nivelEducativoFiltrados.size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		int tamanoPagina = pageable.getPageSize();
		int numeroPagina = pageable.getPageNumber();

		List<NivelEducativo> nivelEducativoPaginados = nivelEducativoFiltrados.stream()
				.skip((long) numeroPagina * tamanoPagina)
				.limit(tamanoPagina)
				.collect(Collectors.toList());

		List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
				.boxed()
				.toList();

        model.addAttribute("PaginaActual", pg);
        model.addAttribute("nPaginas", nPaginas);
        model.addAttribute("nivelesEducativos", nivelEducativoPaginados);
        return "nivelEducativo/nivelEducativo";
    }

    @GetMapping("/nivelEducativo/new")
    public String createUsuarioForm(Model model) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                NivelEducativo nivelEducativo = new NivelEducativo();
                model.addAttribute("nivelEducativo", nivelEducativo);
                model.addAttribute("listaPaises", paisesService.getAllPaises());

                return "nivelEducativo/create_nivelEducativo";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/nivelEducativo")
    public String saveNivelEducativo(@ModelAttribute NivelEducativo nivelEducativo, @RequestParam List<String> paisesSeleccionados) {
        this.validarPerfil();
        nivelEducativoService.saveNivelEducativo(nivelEducativo);

        for (String iso2 : paisesSeleccionados) {
			Paises pais = paisesService.getPaisByISO2(iso2); // Obtener el país por ISO2
			if (pais != null) {
				NivelEducativoPaises relacion = new NivelEducativoPaises();
				relacion.setNivelEducativo(nivelEducativo);
				relacion.setPais(pais); 
				nivelEducativoPaisesService.saveNivelEducativoPaises(relacion);
			}
		}

        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                this.usuario.getCVNombre(), this.perfil.getCVRol(), "Crea en nivel educativo", this.usuario.getOrganizacion().getCodigoPais()));

        return "redirect:/nivelEducativo";

    }

    @GetMapping("/nivelEducativo/{id}")
    public String deleteNivelEducativo(@PathVariable Integer id) {

        try {
            this.validarPerfil();
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                        this.usuario.getCVNombre(), this.perfil.getCVRol(), "Elimina en nivel educativo", this.usuario.getOrganizacion().getCodigoPais()));

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
            if (usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

                model.addAttribute("nivelEducativo", nivelEducativoService.getNivelEducativoById(id));
                return "nivelEducativo/edit_nivelEduactivo";
            } else {
                return "SinAcceso";
            }

        } catch (Exception e) {
            return "SinAcceso";
        }
    }

    @PostMapping("/nivelEducativo/{id}")
    public String updateNivelEducativo(@PathVariable Integer id, @ModelAttribute NivelEducativo nivelEducativo,
            Model model) {
        this.validarPerfil();
        NivelEducativo existingNivelEducativo = nivelEducativoService.getNivelEducativoById(id);
        model.addAttribute("paises", paisesService.getAllPaises());
        existingNivelEducativo.setCI_Id(id);
        existingNivelEducativo.setCVTitulo(nivelEducativo.getCVTitulo());
        existingNivelEducativo.setCVDescripcion(nivelEducativo.getCVDescripcion());
        bitacoraService.saveBitacora(new Bitacora(this.usuario.getCVCedula(),
                this.usuario.getCVNombre(), this.perfil.getCVRol(), "Actualiza en nivel educativo", this.usuario.getOrganizacion().getCodigoPais()));

        nivelEducativoService.updateNivelEducativo(existingNivelEducativo);
        return "redirect:/nivelEducativo";
    }

}
