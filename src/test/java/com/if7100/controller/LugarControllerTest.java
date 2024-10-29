package com.if7100.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Hecho;
import com.if7100.entity.Lugar;
import com.if7100.repository.LugarRepository;
import com.if7100.service.LugarService;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LugarControllerTest {

	
	@Autowired
	private LugarRepository lugarRepository;
	private LugarService lugarServ;
	
	private Integer Hecho=1;
	private String Descripcion="tima";
	private Integer Tipo_Lugar=3;
	private String Direccion="tima";
	private String Ciudad="tima";
	private int codigoPostal=2023;
	private String provincia="Prueba";
	private String canton="Prueba";
	private String distrito="Prueba";

	private Lugar lugar;
	private Lugar LugarConsultado;

	 @BeforeAll
    public void setUp() {
		lugar = new Lugar(Hecho, Descripcion, Tipo_Lugar, Direccion, Ciudad, codigoPostal, provincia, canton, distrito);
    }
	
	@Test
	@Order(1)
	public void Test1_GuardarLugar() throws Exception{
		lugarRepository.save(lugar);
	}
	
	@Test
	@Order(2)
	public void Test2_ConsultarLugar() throws Exception{
		LugarConsultado= lugarRepository.findById(lugar.getCI_Codigo()).get();
		assertEquals(Hecho, LugarConsultado.getCIHecho());
		assertNotEquals(Tipo_Lugar, LugarConsultado.getCIHecho());
	}
	
	@Test
	@Order(3)
	public void Test3_ActualizarLugar() throws Exception{
		LugarConsultado= lugarRepository.findById(lugar.getCI_Codigo()).get();
		LugarConsultado.setCV_Descripcion("Actualizado");

		lugarRepository.save(LugarConsultado);
	}
	
	@Test
	@Order(4)
	public void Test4_EliminarLugar() throws Exception{
		LugarConsultado= lugarRepository.findById(lugar.getCI_Codigo()).get();
		lugarRepository.deleteById(LugarConsultado.getCI_Codigo());
	}
}

