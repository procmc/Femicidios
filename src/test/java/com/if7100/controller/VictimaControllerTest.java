package com.if7100.controller;


import com.if7100.entity.Victima;
import com.if7100.repository.VictimaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VictimaControllerTest {
	
	private int CVDNI=203450876;
	private String CVNombre="Albertina";
	private String CVApellidoPaterno="Chill";
	private String CVApellidoMaterno="Pepper";
	private int CVEdad=30;
	private int CVGenero=2;
	private String CVLugarNac="Cartago";
	private int CVOrientaSex=3;
	private String nacionalidad="Costarricense";
	private int educacion=2;
	private String profesion="Cruz Rojista";
	private String domicilio="Limon centro";
	private String discapacidad="Ninguna";
	private String etnia="Blanca";
	
	private Victima victima = new Victima (CVDNI,CVNombre,CVApellidoPaterno,CVApellidoMaterno,CVEdad,CVGenero,CVLugarNac,  CVOrientaSex,nacionalidad, educacion,
			profesion,domicilio, "Limon", discapacidad, "Todo bien",etnia, "Ninguna", "Ninguna", 0, "Nada");
	
	//private Victima victima = new Victima (CVDNI,"Albertina","Chill","Pepper", 30,2, "Cartago", 3, "Costarricense", 2, "Cruz Rojista", "Limon centro", "Limon", "Ninguna", "Todo bien", "Mestizo", "Ninguna", "Ninguna", 0, "Nada");
	
@Autowired
private VictimaRepository victimaRepository;


private Victima victimaConsultada=new Victima();

@Test
public void Test1() throws Exception {
	
	victimaRepository.save(victima);
	
}

@Test
public void Test2() throws Exception {
	
	victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
	 assertEquals(victimaConsultada.getCVDNI(), CVDNI);
	 assertNotEquals(victimaConsultada.getCVNombre(), CVNombre);
	 assertNotEquals(victimaConsultada.getCVApellidoPaterno(), CVApellidoPaterno);
	 assertNotEquals(victimaConsultada.getCVApellidoMaterno(), CVApellidoMaterno);
	 assertNotEquals(victimaConsultada.getCIEdad(), CVEdad);
	 assertNotEquals(victimaConsultada.getCVGenero(), CVEdad);
	 assertNotEquals(victimaConsultada.getCVGenero(), CVGenero);
	 assertNotEquals(victimaConsultada.getCVLugarNac(), CVLugarNac);
	 assertNotEquals(victimaConsultada.getCVOrientaSex(), CVOrientaSex);
}


@Test
public void Test3() throws Exception {
	
	victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
	victimaConsultada.setCVNombre(CVNombre);
	victimaRepository.save(victimaConsultada);
	victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
	 assertEquals(victimaConsultada.getCVNombre(), CVNombre);
	 assertEquals(victimaConsultada.getCVApellidoPaterno(), CVApellidoPaterno);
	 assertEquals(victimaConsultada.getCVApellidoMaterno(), CVApellidoMaterno);
	 assertEquals(victimaConsultada.getCIEdad(), CVEdad);
	 assertEquals(victimaConsultada.getCVGenero(), CVEdad);
	 assertEquals(victimaConsultada.getCVGenero(), CVGenero);
	 assertEquals(victimaConsultada.getCVLugarNac(), CVLugarNac);
	 assertEquals(victimaConsultada.getCVOrientaSex(), CVOrientaSex);
}

@Test
public void Test4() throws Exception {
	
	victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
	victimaRepository.deleteById(victimaConsultada.getCI_Id());
	 
}

}
