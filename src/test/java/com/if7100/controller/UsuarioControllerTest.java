package com.if7100.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import com.if7100.entity.Organizacion;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {
	
	
@Autowired
private UsuarioService usuarioService;
private UsuarioRepository usuarioRepository;
 
private String Cedula= "604540581";
private String Nombre="David";
private String Apellido="Jimene";
private int perfil=1;
private String Contrasena="Liss";
private int UsuarioAct=1;
private String Nombre2="Reichell";
private Organizacion organizacion;

private Usuario usuario= new Usuario(Cedula, Nombre, Apellido, perfil, UsuarioAct, Contrasena, organizacion);
private Usuario usuarioConsultado= new Usuario();


@Test
public void Test1() throws Exception{
	usuarioService.saveUsuario(usuario);
}

@Test
public void Test2() throws Exception{
	usuarioConsultado= usuarioService.getUsuarioById(1);
	assertEquals(usuarioConsultado.getCVNombre(), Nombre);
	assertNotEquals(usuarioConsultado.getCVApellidos(), Apellido);
}


@Test
public void Test3() throws Exception{
	usuarioService.deleteUsuarioById(7);
}

@Test
public void Test4() throws Exception{
	 Usuario existingUsuario=usuarioService.getUsuarioById(UsuarioAct);
	 existingUsuario.setCI_Id(UsuarioAct);
	 existingUsuario.setCVCedula(usuario.getCVCedula());
	 existingUsuario.setCVNombre("Johnny");
	 existingUsuario.setCVApellidos(usuario.getCVApellidos());
	 existingUsuario.setCIPerfil(usuario.getCIPerfil());
	 existingUsuario.setTCClave(usuario.getTCClave());
	 usuarioService.updateUsuario(existingUsuario);
}

}



