
package com.if7100.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.if7100.entity.Modalidad;
import com.if7100.repository.ModalidadRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModalidadControllerTest {

@Autowired
private ModalidadRepository modalidadRepository;

private String Titulo= "Golpazo";
private String Descripcion= "Cuando le pegan";

private Modalidad modalidad=new Modalidad(Titulo, "Apuñalazo");
private Modalidad modalidadConsultado=new Modalidad();

@Test
public void Test1() throws Exception {
	
	modalidadRepository.save(modalidad);
	
}

@Test
public void Test2() throws Exception {
	
	modalidadConsultado = modalidadRepository.findByCVTitulo(Titulo);
	 assertEquals(modalidadConsultado.getCVTitulo(), Titulo);
	 assertNotEquals(modalidadConsultado.getCVDescripcion(), Titulo);
}


@Test
public void Test3() throws Exception {
	
	modalidadConsultado = modalidadRepository.findByCVTitulo(Titulo);
	modalidadConsultado.setCVDescripcion(Descripcion);
	modalidadRepository.save(modalidadConsultado);
	modalidadConsultado = modalidadRepository.findByCVTitulo(Titulo);
	 assertEquals(modalidadConsultado.getCVDescripcion(), Descripcion);
}

@Test
public void Test4() throws Exception {
	
	modalidadConsultado = modalidadRepository.findByCVTitulo(Titulo);
	 modalidadRepository.deleteById(modalidadConsultado.getCI_Codigo());
	 
}

}
