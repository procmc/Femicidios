package com.if7100.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Lugar;
import com.if7100.repository.LugarRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LugarControllerTest {

	
	@Autowired
	private LugarRepository lugarRepository;
	
	private Integer Hecho=1;
	private String Descripcion="tima";
	private Integer Tipo_Lugar=3;
	private String Direccion="tima";
	private String Ciudad="tima";
	private Integer Pais=506;
	private String Descripcion2="Prueba";
	
	private Lugar lugar= new Lugar(Hecho, "parqueo", 2, "Boston", Ciudad, Pais);
	private Lugar LugarConsultado= new Lugar();
	
	@Test
	public void Test1() throws Exception{
		lugarRepository.save(lugar);
	}
	
	
	@Test
	public void Test2() throws Exception{
		lugarRepository.deleteById(8);
	}
	
	@Test
	public void Test3() throws Exception{
		LugarConsultado= lugarRepository.findById(7).get();
		assertEquals(LugarConsultado.getCIHecho(), Hecho);
		assertNotEquals(LugarConsultado.getCI_Tipo_Lugar(), Tipo_Lugar);
	}
	
	@Test
	public void Test4() throws Exception{
		LugarConsultado= lugarRepository.findById(7).get();
		LugarConsultado.setCV_Descripcion(Descripcion2);
		lugarRepository.save(LugarConsultado);
	}
}

