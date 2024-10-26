package com.if7100.controller;

import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.SituacionJuridica;
import com.if7100.repository.SituacionJuridicaRepository;
import com.if7100.service.SituacionJuridicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SituacionJuridicaControllerTest {

    @Autowired
    private SituacionJuridicaRepository situacionJuridicaRepository;

    @Autowired
    private SituacionJuridicaService situacionJuridicaService;

    private String Titulo = "PruebaSJ";
	private String Descripcion = "situacion juridica test";

	private SituacionJuridica situacionJuridica;
	private SituacionJuridica situacionJuridicaConultada;

	@BeforeAll
	public void setUp() {
		situacionJuridica = new SituacionJuridica(Titulo, Descripcion);
	}

	@Test
	public void Test1() throws Exception {
		situacionJuridicaRepository.save(situacionJuridica);
	}

	@Test
	public void Test2() throws Exception {
		situacionJuridicaConultada = situacionJuridicaRepository.findByCVTitulo(Titulo);
		assertEquals(Titulo, situacionJuridicaConultada.getCVTitulo());
		assertNotEquals("fallo", situacionJuridicaConultada.getCVDescripcion());
	}

	@Test
	public void Test3() throws Exception {
		situacionJuridicaConultada = situacionJuridicaRepository.findByCVTitulo(Titulo);
		situacionJuridicaConultada.setCVDescripcion("Descripcion modificada");
		situacionJuridicaRepository.save(situacionJuridicaConultada);
		
		situacionJuridicaConultada = situacionJuridicaRepository.findByCVTitulo(Titulo);
		assertEquals("Descripcion modificada", situacionJuridicaConultada.getCVDescripcion());
	}

	@Test
	public void Test4() throws Exception {
		situacionJuridicaConultada = situacionJuridicaRepository.findByCVTitulo(Titulo);
		situacionJuridicaRepository.deleteById(situacionJuridicaConultada.getCI_Codigo());
	}

}
