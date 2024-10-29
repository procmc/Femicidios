package com.if7100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.OrientacionSexual;
import com.if7100.repository.OrientacionSexualRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrientacionSexualControllerTest {

	@Autowired
	private OrientacionSexualRepository orientacionRepository;

	private String Titulo = "Monchi";
	private String Descripcion = "Valerin";

	private OrientacionSexual orientacion;
	private OrientacionSexual orientacionConsultada;

	@BeforeAll
	public void setUp() {
		orientacion = new OrientacionSexual(Titulo, Descripcion);
	}

	@Test
	@Order(1)
	public void Test1_GuardarOrientacionSexual() throws Exception {
		orientacionRepository.save(orientacion);
	}

	@Test
	@Order(2)
	public void Test2_ConsultarOrientacionSexual() throws Exception {
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		assertEquals(Titulo, orientacionConsultada.getCVTitulo());
		assertNotEquals("fallo", orientacionConsultada.getCVDescripcion());
	}

	@Test
	@Order(3)
	public void Test3_ActualizarOrientacionSexual() throws Exception {
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		orientacionConsultada.setCVDescripcion("Descripcion modificada");
		orientacionRepository.save(orientacionConsultada);
		
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		assertEquals("Descripcion modificada", orientacionConsultada.getCVDescripcion());
	}

	@Test
	public void Test4_EliminarOrientacionSexual() throws Exception {
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		orientacionRepository.deleteById(orientacionConsultada.getCI_Codigo());
	}

}
