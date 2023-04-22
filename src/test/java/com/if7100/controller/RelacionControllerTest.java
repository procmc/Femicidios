package com.if7100.controller;

import javax.management.relation.Relation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Relacion;
import com.if7100.repository.RelacionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RelacionControllerTest {
	
	@Autowired
	private RelacionRepository relacionRepository;
	
	private String Titulo="Compañero";
	private String Drecri="Compañero de la escuela";
	
	private Relacion relation = new Relacion(Titulo,"nada");

	@Test
	public void Test1() throws Exception{
		relacionRepository.save(relation);
	}
			

}
