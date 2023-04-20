package com.if7100.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private String Nombre = "Monchi";
	private String Apellido = "Valerin";
	
	private Usuario usuario = new Usuario (Nombre, "LOS");
	private Usuario usuarioConsultado = new Usuario();
	
	
	@Test
	public void Test1() throws Exception {
		
		usuarioRepository.save(usuario);
		
	}
	
	@Test
	public void Test2() throws Exception {
		
		usuarioConsultado = usuarioRepository.findByCVNombre(Nombre);
		assertEquals(usuarioConsultado.getCVNombre(),Nombre);
		assertNotEquals(usuarioConsultado.getCVApellidos(),Apellido);
	}
	
	
	@Test
	public void Test3() throws Exception {
		
		usuarioConsultado = usuarioRepository.findByCVNombre(Nombre);
		usuarioConsultado.setCVApellidos(Apellido);
		usuarioRepository.save(usuarioConsultado);
		usuarioConsultado = usuarioRepository.findByCVNombre(Nombre);
		assertEquals(usuarioConsultado.getCVApellidos(),Apellido);
	}
	
	
	@Test
	public void Test4() throws Exception {
		
		usuarioConsultado = usuarioRepository.findByCVNombre(Nombre);		
		usuarioRepository.deleteById(usuarioConsultado.getCI_Id());
		
	}
	
}
