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
	@Order(1)
	public void Test1_GuardarNivelEducativo() throws Exception {

		nivelEducativoRepository.save(nivelEducativo);

	}

	@Test
	@Order(2)
	public void test2_ConsultarNivelEducativo() throws Exception {
		nivelEducativo = nivelEducativoRepository.findByCVTitulo(Titulo);
		assertEquals(Titulo, nivelEducativo.getCVTitulo());
		assertNotEquals("aefe", nivelEducativo.getCVDescripcion());
	}

	@Test
	@Order(3)
	public void test3_ActualizarNivelEducativo() throws Exception {
		nivelEducativoConsultada = nivelEducativoRepository.findByCVTitulo(Titulo);
		nivelEducativoConsultada.setCVDescripcion("descripcion modificada");
		nivelEducativoRepository.save(nivelEducativoConsultada);
		assertEquals("descripcion modificada", nivelEducativoConsultada.getCVDescripcion());
	}

	@Test
	@Order(4)
	public void test4_EliminarNivelEducativo() throws Exception {
		nivelEducativoConsultada = nivelEducativoRepository.findByCVTitulo(Titulo);
		nivelEducativoRepository.deleteById(nivelEducativoConsultada.getCI_Id());
	}
}
