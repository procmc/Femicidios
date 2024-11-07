package com.if7100.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;
import com.if7100.entity.Organizacion;
import com.if7100.entity.Paises;
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;
import com.if7100.service.UsuarioPerfilService;

@Controller
public class BitacoraController {

    private BitacoraService bitacoraService;
	private UsuarioPerfilService usuarioPerfilService;
    //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
	private UsuarioPerfilRepository usuarioPerfilRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    //instancias para control de bitacora
    private Usuario usuario;
  //  private Bitacora bitacora1;

    public BitacoraController(BitacoraService  bitacoraService, PerfilService perfilService, UsuarioRepository usuarioRepository,
		UsuarioPerfilService usuarioPerfilService, UsuarioPerfilRepository usuarioPerfilRepository) {
        super();
        this.bitacoraService = bitacoraService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
		this.usuarioPerfilService = usuarioPerfilService;
		this.usuarioPerfilRepository = usuarioPerfilRepository;
      
       // this.bitacora1 = bitacora1;Bitacora bitacora1,
    }

private void validarPerfil() {

		try {
			Usuario usuario=new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));
			
			List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilRepository.findByUsuario(this.usuario);

            this.perfil = new Perfil(
                    perfilService.getPerfilById(usuarioPerfiles.get(0).getPerfil().getCI_Id()));


		}catch (Exception e) {
			// TODO: handle exception
		}

	}

	private Pageable initPages(int pg, int paginasDeseadas, int numeroTotalElementos){
		int numeroPagina = pg-1;
		if (numeroTotalElementos < 10){
			paginasDeseadas = 1;
		}
		if (numeroTotalElementos < 1){
			numeroTotalElementos = 1;
		}
		int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);
		return PageRequest.of(numeroPagina, tamanoPagina);
	}

    @GetMapping("/bitacoras")
    public String listBitacoras(Model model){
		this.validarPerfil();
        return "redirect:/bitacora/1";
    }

	@GetMapping("/bitacora/{pg}")
	public String listBiracora(Model model, @PathVariable Integer pg){

		this.validarPerfil();
		if (pg < 1){
			return "redirect:/bitacora/1";
		}

		 // obtiene la organizacion del usuario
        Organizacion organizacion = this.usuario.getOrganizacion();

        // obtiene el id del pais del hecho segun el pais de la organizacion del usuario
        List<Bitacora> bitacoraFiltrados = bitacoraService.findByCodigoPais(organizacion.getCodigoPais());

        int numeroTotalElementos = bitacoraFiltrados.size();

        Pageable pageable = initPages(pg, 5, numeroTotalElementos);

        int tamanoPagina = pageable.getPageSize();
        int numeroPagina = pageable.getPageNumber();

        List<Bitacora> bitacoraPaginados = bitacoraFiltrados.stream()
                .skip((long) numeroPagina * tamanoPagina)
                .limit(tamanoPagina)
                .collect(Collectors.toList());

        List<Integer> nPaginas = IntStream.rangeClosed(1, (int) Math.ceil((double) numeroTotalElementos / tamanoPagina))
                .boxed()
                .toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("bitacoras", bitacoraPaginados);
		return "bitacoras/bitacoras";
	}

    @GetMapping("/bitacoras/new")
    public String createBitacorasForm(Model model){
    	try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {
				Bitacora bitacora= new Bitacora();
		        model.addAttribute("bitacora", bitacora);
		        return "bitacoras/create_bitacora";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}

    }

    @PostMapping("/bitacoras")
    public String saveBitacora(@ModelAttribute Bitacora bitacora){
    	bitacoraService.saveBitacora(bitacora);
        return "redirect:/bitacoras";
    }

    @GetMapping("/bitacoras/{id}")
    public String deleteBitacora(@PathVariable Integer id){

    	try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				//INSERTAR EN BITACORA
				//String descripcion="Elimino en Bitacora";
			    //Bitacora bitacora = new Bitacora(this.usuario.getCI_Id(), this.usuario.getCVNombre(),this.perfil.getCVRol(),descripcion);
               // bitacoraService.saveBitacora(bitacora);
                
				bitacoraService.deleteBitacoraById(id);
		        return "redirect:/bitacoras";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }

    @GetMapping("/bitacoras/edit/{id}")
    public String editBitacoraForm(@PathVariable Integer id, Model model){


    	try {
			this.validarPerfil();
			if(usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 1)
            || usuarioPerfilService.usuarioTieneRol(this.usuario.getCVCedula(), 2)) {

				model.addAttribute("bitacora", bitacoraService.getBitacoraById(id));
		        return "bitacoras/edit_bitacora";
			}else {
				return "SinAcceso";
			}

		}catch (Exception e) {
			return "SinAcceso";
		}
    }


    @PostMapping("/bitacoras/{id}")
    public String updateModalidad(@PathVariable Integer id, @ModelAttribute Bitacora bitacora){
        Bitacora existingBitacora = bitacoraService.getBitacoraById(id);
        existingBitacora.setCIIdBitacora(id);
        existingBitacora.setCVUsuario(bitacora.getCVUsuario());
        existingBitacora.setCVDescripcion(bitacora.getCVDescripcion());
        existingBitacora.setCVRol(bitacora.getCVRol());
        existingBitacora.setCTFecha(new Date());
        bitacoraService.updateBitacora(existingBitacora);
        return "redirect:/bitacoras";
    }

}
