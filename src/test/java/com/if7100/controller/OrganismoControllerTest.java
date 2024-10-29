package com.if7100.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Order;

import com.if7100.entity.Organismo;

import com.if7100.repository.OrganismoRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganismoControllerTest {
	@Autowired
	private OrganismoRepository organismoRepository;
	private String str="aaa";
	Organismo org=new Organismo("aaa","aaa", "aaa", "aaa", 52);
	Organismo consultado= new Organismo();
	@Test
	@Order(1)
	private void Test1_GuardarOrganismo() throws Exception{
		organismoRepository.save(org);
	}
	@Test
	private void Test2_ConsultarOrganismo() throws Exception{
		consultado=organismoRepository.findByCVNombre("aaa");
		assertEquals(consultado.getCVRol(),"aaa");
		assertNotEquals(consultado.getCodigoPais(),"aaa");
	}
	
	@Test
	@Order(3)
	private void Test3_ActualizarOrganismo() throws Exception{
		consultado=organismoRepository.findByCVNombre("aaa");
		consultado.setCVRol("aaa");
		organismoRepository.save(consultado);
		consultado=organismoRepository.findByCVNombre("aaa");
		assertNotEquals(consultado.getCVRol(),"aaa");
	}
	
	@Test
	private void Test4_EliminarOrganismo() throws Exception{
		consultado=organismoRepository.findByCVNombre("aaa");
		organismoRepository.deleteById(consultado.getCI_Id());
	}

}
