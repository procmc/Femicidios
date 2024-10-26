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
	public void Test1() throws Exception {
		orientacionRepository.save(orientacion);
	}

	@Test
	public void Test2() throws Exception {
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		assertEquals(Titulo, orientacionConsultada.getCVTitulo());
		assertNotEquals("fallo", orientacionConsultada.getCVDescripcion());
	}

	@Test
	public void Test3() throws Exception {
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		orientacionConsultada.setCVDescripcion("Descripcion modificada");
		orientacionRepository.save(orientacionConsultada);
		
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		assertEquals("Descripcion modificada", orientacionConsultada.getCVDescripcion());
	}

	@Test
	public void Test4() throws Exception {
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		orientacionRepository.deleteById(orientacionConsultada.getCI_Codigo());
	}

}
