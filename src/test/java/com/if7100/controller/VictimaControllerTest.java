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
	private Victima victima = new Victima (203450876,"Albertina","Chill","Pepper", 30,2, "Cartago", 3);
	
	
	@Autowired
	private VictimaRepository victimaRepository; 
	
	@Test
	public void Test1() throws Exception{
		victimaRepository.save(victima);
	}
	
	
	
	

}
