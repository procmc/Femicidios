package com.if7100.controller;

import java.util.Date;
import java.util.List;
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
import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.BitacoraService;
import com.if7100.service.PerfilService;

@Controller
public class BitacoraController {

    private BitacoraService bitacoraService;
    //instancias para control de acceso
    private UsuarioRepository usuarioRepository;
    private Perfil perfil;
    private PerfilService perfilService;
    //instancias para control de bitacora
    private Usuario usuario;
  //  private Bitacora bitacora1;

    public BitacoraController(BitacoraService  bitacoraService, PerfilService perfilService, UsuarioRepository usuarioRepository) {
        super();
        this.bitacoraService = bitacoraService;
        this.perfilService = perfilService;
        this.usuarioRepository = usuarioRepository;
      
       // this.bitacora1 = bitacora1;Bitacora bitacora1,
    }

private void validarPerfil() {

		try {
			Usuario usuario=new Usuario();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			this.usuario = new Usuario(usuarioRepository.findByCVCedula(username));
			this.perfil = new Perfil(perfilService.getPerfilById(usuarioRepository.findByCVCedula(username).getCIPerfil()));

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
        return "redirect:/bitacora/1";
    }

	@GetMapping("/bitacora/{pg}")
	public String listBiracora(Model model, @PathVariable Integer pg){

		if (pg < 1){
			return "redirect:/bitacora/1";
		}

		int numeroTotalElementos = bitacoraService.getAllBitacoras().size();

		Pageable pageable = initPages(pg, 5, numeroTotalElementos);

		Page<Bitacora> bitacoraPage = bitacoraService.getAllBitacorasPage(pageable);

		List<Integer> nPaginas = IntStream.rangeClosed(1, bitacoraPage.getTotalPages())
				.boxed()
				.toList();

		model.addAttribute("PaginaActual", pg);
		model.addAttribute("nPaginas", nPaginas);
		model.addAttribute("bitacoras", bitacoraPage.getContent());
		return "bitacoras/bitacoras";
	}

    @GetMapping("/bitacoras/new")
    public String createBitacorasForm(Model model){
    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {
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
    public String saveBitacora(@ModelAttribute("bitacora")Bitacora bitacora){
    	bitacoraService.saveBitacora(bitacora);
        return "redirect:/bitacoras";
    }

    @GetMapping("/bitacoras/{id}")
    public String deleteBitacora(@PathVariable Integer id){

    	try {
			this.validarPerfil();
			if(!this.perfil.getCVRol().equals("Consulta")) {

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
			if(!this.perfil.getCVRol().equals("Consulta")) {

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
    public String updateModalidad(@PathVariable Integer id, @ModelAttribute("bitacora") Bitacora bitacora){
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
