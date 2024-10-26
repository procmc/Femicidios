package com.if7100.controller;

import com.if7100.entity.Imputado;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.repository.ProcesoJudicialRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProcesoJudicialControllerTest {

	@Autowired
	private ProcesoJudicialRepository procesoJudicialRepository;

	private String CVEstado = "Prueba estado";
	Date fecha = Date.valueOf("2024-10-25");
	private int CIPersonasImputadas = 1;
	private String CVAgravantes = "Ninguna";
	private String CVTipoDelito = "Ninguna";

	private ProcesoJudicial proceso;

	private ProcesoJudicial consultado;

	@BeforeAll
	public void setUp() {
		proceso = new ProcesoJudicial(CVEstado, fecha, CIPersonasImputadas, CVAgravantes, CVTipoDelito);
	}

	@Test
	@Order(1)
	public void Test1() throws Exception {
		procesoJudicialRepository.save(proceso);
	}

	@Test
	@Order(2)
	public void testDos() throws Exception {
		consultado = procesoJudicialRepository.findByCVEstado(CVEstado);
		assertEquals(CIPersonasImputadas, consultado.getCIPersonasImputadas());
		assertNotEquals("Prueba fallo", consultado.getCVAgravantes());
	}

	@Test
	@Order(3)
	public void testTres() throws Exception {
		consultado = procesoJudicialRepository.findByCVEstado(CVEstado);
		consultado.setCVAgravantes("Agravantes modificados");
		procesoJudicialRepository.save(consultado);

		consultado = procesoJudicialRepository.findByCVEstado(CVEstado);
		assertNotEquals(CVEstado, consultado.getCVAgravantes());
	}


	@Test
	@Order(4)
	public void testCuatro() throws Exception {
		consultado = procesoJudicialRepository.findByCVEstado(CVEstado);
		procesoJudicialRepository.delete(consultado);
	}

}
