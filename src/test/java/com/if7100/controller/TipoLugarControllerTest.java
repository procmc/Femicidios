package com.if7100.controller;


import com.if7100.entity.TipoLugar;
import com.if7100.repository.TipoLugarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TipoLugarControllerTest {

    @Autowired
    private TipoLugarRepository tipoLugarRepository;

    private String Titulo= "prueba 1";
	private String Descripcion= "1";
	private TipoLugar tipoLugar = new TipoLugar(Titulo, "Cambio");
	private TipoLugar tipoLugarConsultado = new TipoLugar();
	
	@Test 
	public void Test1_Agregar() throws Exception{//agrega un tipo de lugar
		tipoLugarRepository.save(tipoLugar);
	}
	
	@Test 
	public void Test2_Actualizar() throws Exception{//busca un tipoLugar por titulo
		tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
		assertEquals(tipoLugarConsultado.getCVTitulo(), Titulo);	
		assertNotEquals(tipoLugarConsultado.getCVDescripcion(), Descripcion);//busca si la descripcion es diferente	
	}
	
	@Test 
	public void Test3() throws Exception{//busca un tipolugar por titulo cambia la Descripcion y pregunta si ahora la Descripcion son iguales
		tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
		tipoLugarConsultado.setCVDescripcion(Descripcion);
		tipoLugarRepository.save(tipoLugarConsultado);
		
		tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
		assertEquals(tipoLugarConsultado.getCVDescripcion(), Descripcion);	

	}
	
	@Test 
	public void Test4() throws Exception{//busca por titulo y lo elimina
		tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
		
		tipoLugarRepository.deleteById(tipoLugarConsultado.getCI_Codigo());
	}

}
