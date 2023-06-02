package com.if7100.controller;

import com.if7100.entity.TipoRelacion;
import com.if7100.repository.TipoRelacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TipoRelacionControllerTest {

    @Autowired
    private TipoRelacionRepository tipoRelacionRepository;

    private Integer codigo = 1;

    private String titulo = "Vinculado";

    private String descripcion = "La Victima vinculada al crimen principal";

    private TipoRelacion tipoRelacion = new TipoRelacion("Se desconoce", "Se desconoce el tipo de relación.");

    private  TipoRelacion tipoRelacionConsultada = new TipoRelacion();

    @Test
    public void testUno() throws  Exception{
        tipoRelacionRepository.save(tipoRelacion);
    }

    @Test
    public void testDos() throws Exception{
        //testUno();
        tipoRelacionConsultada = tipoRelacionRepository.findByCVTitulo(titulo);
        System.out.println(tipoRelacionConsultada.getCVTitulo() + tipoRelacionConsultada.getCVDescripcion());
        assertEquals(tipoRelacionConsultada.getCVTitulo(), titulo);
        assertNotEquals(tipoRelacionConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testTres() throws Exception{
        tipoRelacionConsultada = tipoRelacionRepository.findByCVTitulo(titulo);
        tipoRelacionConsultada.setCVDescripcion(descripcion);
        tipoRelacionRepository.save(tipoRelacionConsultada);
        assertEquals(tipoRelacionConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testCuatro() throws Exception{
        tipoRelacionConsultada = tipoRelacionRepository.findByCVTitulo(titulo);
        tipoRelacionRepository.deleteById(tipoRelacionConsultada.getCI_Codigo());
    }
    
}
