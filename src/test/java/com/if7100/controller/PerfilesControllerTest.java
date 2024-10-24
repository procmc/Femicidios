package com.if7100.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Perfil;
import com.if7100.repository.PerfilRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerfilesControllerTest {
	@Autowired
	private PerfilRepository perfilRepository;
	private String str="aaa";
	Perfil org=new Perfil("Hago todo","SOYADMIN");
	Perfil consultado= new Perfil();
	@Test
	private void Test1() throws Exception{
		perfilRepository.save(org);
	}
	@Test
	private void Test2() throws Exception{
		consultado=perfilRepository.findByCVDescripcion("Hago todo");
		assertEquals(consultado.getCVRol(),"Hago todo");
		assertNotEquals(consultado.getCVDescripcion(),"Hago todo");
	}
	
	@Test
	private void Test3() throws Exception{
		consultado=perfilRepository.findByCVDescripcion("Hago todo");
		consultado.setCVRol("aaa");
		perfilRepository.save(consultado);
		consultado=perfilRepository.findByCVDescripcion("Hago todo");
		assertNotEquals(consultado.getCVRol(),"SOYADMIN");
	}
	
	@Test
	private void Test4() throws Exception{
		consultado=perfilRepository.findByCVDescripcion("Hago todo");
		perfilRepository.deleteById(consultado.getCI_Id());
	}
}
