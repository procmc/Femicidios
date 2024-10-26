package com.if7100.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.NivelEducativo;

import com.if7100.repository.NivelEducativoRepository;

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
public class NivelEducativoControllerTest {

	@Autowired
	private NivelEducativoRepository nivelEducativoRepository;

	private String Titulo = "prueba uni";
	private String Descripion = "descripcion de prueba uni";
	private String Pais = "Costa Rica";

	private NivelEducativo nivelEducativo;
	private NivelEducativo nivelEducativoConsultada;

	@BeforeAll
	public void setUp() {
		nivelEducativo = new NivelEducativo(Titulo, Descripion, Pais);
	}

	@Test
	public void Test1() throws Exception {

		nivelEducativoRepository.save(nivelEducativo);

	}

	@Test
	public void test2() throws Exception {
		nivelEducativo = nivelEducativoRepository.findByCVTitulo(Titulo);
		assertEquals(Titulo, nivelEducativo.getCVTitulo());
		assertNotEquals("aefe", nivelEducativo.getCVDescripcion());
	}

	@Test
	public void test3() throws Exception {
		nivelEducativoConsultada = nivelEducativoRepository.findByCVTitulo(Titulo);
		nivelEducativoConsultada.setCVDescripcion("descripcion modificada");
		nivelEducativoRepository.save(nivelEducativoConsultada);
		assertEquals("descripcion modificada", nivelEducativoConsultada.getCVDescripcion());
	}

	@Test
	public void test4() throws Exception {
		nivelEducativoConsultada = nivelEducativoRepository.findByCVTitulo(Titulo);
		nivelEducativoRepository.deleteById(nivelEducativoConsultada.getCI_Id());
	}
}
