package com.if7100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Organizacion;
import com.if7100.entity.Usuario;
import com.if7100.repository.OrganizacionRepository;
import com.if7100.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.sql.Date;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OrganizacionRepository organizacionRepository;

	private String Cedula = "0011223344";
	private String Nombre = "David";
	private String Apellido = "Jimene";
	private String Contrasena = "Liss";
	private Organizacion organizacion = new Organizacion( "nombreO", "DireccionO", "TelefonoO", "CorreoO", 52);

	private Usuario usuario;
	private Usuario usuarioConsultado;

	@BeforeAll
	public void setUp() {
		organizacionRepository.save(organizacion);
		
		usuario = new Usuario(Cedula, Nombre, Apellido, Contrasena, organizacion);
	}

	@Test
	@Order(1)
	public void Test1_GuardarUsuario() throws Exception {
		usuarioRepository.save(usuario);
	}

	@Test
	@Order(2)
	public void Test2_ConsultarUsuario() throws Exception {
		usuarioConsultado = usuarioRepository.findByCVCedula(Cedula);
		assertEquals(usuarioConsultado.getCVNombre(), Nombre);
		assertNotEquals(Nombre, usuarioConsultado.getCVApellidos());
	}

	@Test
	@Order(3)
	public void Test3_ActualizarUsuario() throws Exception {
		usuarioConsultado = usuarioRepository.findByCVCedula(Cedula);
		usuarioConsultado.setCVApellidos("Jimenez");
		usuarioRepository.save(usuarioConsultado);

		assertEquals("Jimenez", usuarioConsultado.getCVApellidos());
	}

	@Test
	@Order(4)
	public void Test4_EliminarUsuario() throws Exception{
		usuarioRepository.deleteById(usuarioConsultado.getCI_Id());
		organizacionRepository.deleteById(organizacion.getCI_Codigo_Organizacion());
	}

}
