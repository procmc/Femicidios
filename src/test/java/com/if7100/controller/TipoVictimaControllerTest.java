package com.if7100.controller;
import com.if7100.entity.Modalidad;
import com.if7100.entity.TipoVictima;
import com.if7100.repository.TipoVictimaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoVictimaControllerTest {

    @Autowired
    private TipoVictimaRepository tipoVictimaRepository;

    private String titulo = "Principal";
    private String descripcion = "La victima principal del feminicidio";

    private TipoVictima tipoVictima;
    private  TipoVictima tipoVictimaConsultada;

    @BeforeAll
    public void setUp() {
        tipoVictima = new TipoVictima(titulo, descripcion);
    }


    @Test
    @Order(1)
    public void testUno() throws  Exception{
        tipoVictimaRepository.save(tipoVictima);
    }

    @Test
    @Order(2)
    public void testDos() throws Exception{
        tipoVictimaConsultada = tipoVictimaRepository.findByCVTitulo(titulo);
        assertEquals(titulo, tipoVictimaConsultada.getCVTitulo());
        assertNotEquals(titulo, tipoVictimaConsultada.getCVDescripcion());
    }

    @Test
    @Order(3)
    public void testTres() throws Exception{
        tipoVictimaConsultada = tipoVictimaRepository.findByCVTitulo(titulo);
        tipoVictimaConsultada.setCVDescripcion("descripcion modificada");

        tipoVictimaRepository.save(tipoVictimaConsultada);
        assertEquals("descripcion modificada", tipoVictimaConsultada.getCVDescripcion());
    }

    @Test
    @Order(4)
    public void testCuatro() throws Exception{
        tipoVictimaConsultada = tipoVictimaRepository.findByCVTitulo(titulo);
        tipoVictimaRepository.deleteById(tipoVictimaConsultada.getCI_Codigo());
    }

}
