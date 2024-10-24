package com.if7100.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Lugar;
import com.if7100.repository.LugarRepository;
import com.if7100.service.LugarService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

	private Lugar lugar= new Lugar(Hecho, Descripcion, Tipo_Lugar, Direccion, Ciudad, codigoPostal, provincia, canton, distrito);
	private Lugar LugarConsultado= new Lugar();
	
	@Test
	public void Test1() throws Exception{
		lugarRepository.save(lugar);
	}
	
	@Test
	public void Test2() throws Exception{
		LugarConsultado= lugarRepository.findById(5).get();
		assertEquals(LugarConsultado.getCIHecho(), Hecho);
		assertNotEquals(LugarConsultado.getCITipoLugar(), Tipo_Lugar);
	}
	
	@Test
	public void Test3() throws Exception{
		LugarConsultado= lugarRepository.findById(5).get();
		LugarConsultado.setCI_Codigo(5);
		LugarConsultado.setCIHecho(LugarConsultado.getCIHecho());
		LugarConsultado.setCV_Descripcion("hhhhhh");
		LugarConsultado.setCITipoLugar(lugar.getCITipoLugar());
		LugarConsultado.setCV_Direccion(lugar.getCV_Direccion());
		LugarConsultado.setCV_Ciudad(lugar.getCV_Ciudad());
		LugarConsultado.setCI_Codigo_Postal(lugar.getCI_Codigo_Postal());
		lugarRepository.save(LugarConsultado);
	}
	
	@Test
	public void Test4() throws Exception{
		lugarRepository.deleteById(8);
	}
}

