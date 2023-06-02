package com.if7100.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.OrientacionSexual;
import com.if7100.repository.OrientacionSexualRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrientacionSexualControllerTest {

	@Autowired
	private OrientacionSexualRepository orientacionRepository;
	
	private String Titulo = "Monchi";
	private String Descripcion = "Valerin";
	
	private OrientacionSexual orientacion = new OrientacionSexual (Titulo, "LOS");
	private OrientacionSexual orientacionConsultada = new OrientacionSexual();
	
	
	@Test
	public void Test1() throws Exception {
		
		orientacionRepository.save(orientacion);
		
	}
	
	@Test
	public void Test2() throws Exception {
		
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		assertEquals(orientacionConsultada.getCVTitulo(),Titulo);
		assertNotEquals(orientacionConsultada.getCVDescripcion(),Descripcion);
	}
	
	
	@Test
	public void Test3() throws Exception {
		
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		orientacionConsultada.setCVDescripcion(Descripcion);
		orientacionRepository.save(orientacionConsultada);
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);
		assertEquals(orientacionConsultada.getCVDescripcion(),Descripcion);
	}
	
	
	@Test
	public void Test4() throws Exception {
		
		orientacionConsultada = orientacionRepository.findByCVTitulo(Titulo);		
		orientacionRepository.deleteById(orientacionConsultada.getCI_Codigo());
		
	}
	
}
