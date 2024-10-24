package com.if7100.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.SecurityUserDetailsService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	private SecurityUserDetailsService userDetailsService;
	
	
	@Test
	public void testLoadUserByUsername() throws Exception{
	    // Create a mock UserRepository
		usuarioRepository = mock(UsuarioRepository.class);

		//Crear un usuario de prueba
		String username = "70274061";
		String password = "12345Jm#";
		Usuario usuario = new Usuario();
		usuario.setCVCedula("70274061");
		usuario.setTCClave("12345Jm#");

		//Configura el mock UserRepository para que devuelva el usuario de prueba cuando se llame con "username".
	    when(usuarioRepository.findByCVCedula(username)).thenReturn(usuario);

	    // Crea un objeto UserDetailsService utilizando el mock UserRepository.
	   userDetailsService = new SecurityUserDetailsService(usuarioRepository);

	    // Llama al método loadUserByUsername en el objeto UserDetailsService.
	    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	    // Verifica que el objeto UserDetails devuelto por el método loadUserByUsername tenga el nombre de usuario y la contraseña correctos.
	    assertEquals(username, userDetails.getUsername());
	    assertEquals(password, userDetails.getPassword());
	}
}

