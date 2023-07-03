package com.if7100.controller;

import com.if7100.entity.Imputado;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.repository.ProcesoJudicialRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcesoJudicialControllerTest {
	private ProcesoJudicial proceso = new ProcesoJudicial (1010, "Completado", 1,"Agravante1","Homicidio");
	
	ProcesoJudicial consultado= new ProcesoJudicial();
	
	@Autowired
	private ProcesoJudicialRepository procesoJudicialRepository; 
	
	@Test
	public void Test1() throws Exception{
		procesoJudicialRepository.save(proceso);
	}
	
	/*
	 * @Test public void testDos() throws Exception{
	 * consultado=procesoJudicialRepository.findByCIDenunciante(701230456);
	 * assertEquals(consultado.getCIDenunciante(), 701230456);
	 * assertNotEquals(consultado.getCIDenunciante(), 110510384); }
	 * 
	 * @Test public void testTres() throws Exception{
	 * consultado=procesoJudicialRepository.findByCIDenunciante(701230456);
	 * consultado.setCIDenunciante(701230456);
	 * procesoJudicialRepository.save(consultado);
	 * consultado=procesoJudicialRepository.findByCIDenunciante(701230456);
	 * assertNotEquals(consultado.getCIDenunciante(),701230456); }
	 * 
	 * @Test public void testCuatro() throws Exception{ consultado =
	 * procesoJudicialRepository.findByCIDenunciante(701230456);
	 * procesoJudicialRepository.delete(consultado); }
	 */
	
	

}
