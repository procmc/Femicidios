package com.if7100.controller;

import com.if7100.entity.TipoRelacion;
import com.if7100.repository.TipoRelacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.sql.Date;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoRelacionControllerTest {

    @Autowired
    private TipoRelacionRepository tipoRelacionRepository;

    private String titulo = "Vinculado";
    private String descripcion = "La Victima vinculada al crimen principal";
    private String pais = "Costa Rica";


    private TipoRelacion tipoRelacion;
    private  TipoRelacion tipoRelacionConsultada;

    @BeforeAll
    public void setUp() {
        tipoRelacion = new TipoRelacion(titulo, descripcion, pais);
    }

    @Test
    @Order(1)
    public void testUno() throws  Exception{
        tipoRelacionRepository.save(tipoRelacion);
    }

    @Test
    @Order(2)
    public void testDos() throws Exception{
        tipoRelacionConsultada = tipoRelacionRepository.findByCVTitulo(titulo);
        assertEquals(titulo, tipoRelacionConsultada.getCVTitulo());
        assertNotEquals(titulo, tipoRelacionConsultada.getCVDescripcion());
    }

    @Test
    @Order(3)
    public void testTres() throws Exception{
        tipoRelacionConsultada = tipoRelacionRepository.findByCVTitulo(titulo);
        tipoRelacionConsultada.setCVDescripcion("descripcion modificada");
        tipoRelacionRepository.save(tipoRelacionConsultada);


        assertEquals("descripcion modificada", tipoRelacionConsultada.getCVDescripcion());
    }

    @Test
    @Order(4)
    public void testCuatro() throws Exception{
        tipoRelacionConsultada = tipoRelacionRepository.findByCVTitulo(titulo);
        tipoRelacionRepository.deleteById(tipoRelacionConsultada.getCI_Codigo());
    }
    
}
