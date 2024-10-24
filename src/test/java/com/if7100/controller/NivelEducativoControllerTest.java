package com.if7100.controller;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.NivelEducativo;

import com.if7100.repository.NivelEducativoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class NivelEducativoControllerTest {
	
	@Autowired
	private NivelEducativoRepository nivelEducativoRepository;
	
	private String Titulo ="secundaria baja";
	private String Descripion ="Educacion secundaria baja";
	
	
	
	private NivelEducativo nivelEducativo = new NivelEducativo(Titulo, "Educacion secundaria baja","Costa Rica");
	@Test
	public void Test1() throws Exception{
		
		nivelEducativoRepository.save(nivelEducativo);

}
	 @Test
	    public void test2() throws Exception{
	        Test1();
	       nivelEducativo = nivelEducativoRepository.findByCVTitulo(Titulo);
	        assertEquals(nivelEducativo.getCVTitulo(), Titulo);
	        assertNotEquals(nivelEducativo.getCVDescripcion(), Descripion);
	    }

	    @Test
	    public void test3() throws Exception{
//	      nivelEducativoConsultada = nivelEducativoRepository.findByCV_Titulo(titulo);
//	        nivelEducativoConsultada.setCV_Descripcion(descripcion);
//	       nivelEducativoRepository.save(nivelEducativoConsultada);
//	        assertEquals(nivelEducativoConsultada.getCV_Descripcion(), descripcion);
	    }

	    @Test
	    public void test4() throws Exception{
//	      nivelEducativoConsultada = nivelEducativoRepository.findByCV_Titulo(titulo);
//	        nivelEducativoRepository.deleteById(nivelEducativoConsultada.getCI_Id());
	    }
}
