package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.Modalidad;
import com.if7100.repository.ModalidadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModalidadControllerTest {

    @Autowired
    private ModalidadRepository modalidadRepository;

    private String titulo = "Golpes";
    private String descripcion = "Asesinada por golpes";
    private String pais = "Costa Rica";

    private Modalidad modalidad;
    private  Modalidad modalidadConsultada;

        @BeforeAll
    public void setUp() {
        modalidad = new Modalidad( titulo, descripcion, pais);
    }

    @Test
    @Order(1)
    public void testUno_GuardarModalidad() throws  Exception{
        modalidadRepository.save(modalidad);
    }

    @Test
    @Order(2)
    public void testDos_ConsultarModalidad() throws Exception{
        modalidadConsultada = modalidadRepository.findByCVTitulo(titulo);
        assertEquals(titulo, modalidadConsultada.getCVTitulo());
        assertNotEquals( "34de", modalidadConsultada.getCVDescripcion());
    }

    @Test
    @Order(3)
    public void testTres_ActualizarModalidad() throws Exception{
        modalidadConsultada = modalidadRepository.findByCVTitulo(titulo);
        modalidadConsultada.setCVDescripcion("descripcion modificada");
        modalidadRepository.save(modalidadConsultada);
        assertEquals("descripcion modificada", modalidadConsultada.getCVDescripcion());
    }

    @Test
    @Order(4)
    public void testCuatro_EliminarModalidad() throws Exception{
        modalidadConsultada = modalidadRepository.findByCVTitulo(titulo);
        modalidadRepository.deleteById(modalidadConsultada.getCI_Codigo());
    }

}
