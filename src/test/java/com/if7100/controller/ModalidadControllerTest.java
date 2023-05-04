package com.if7100.controller;

import com.if7100.entity.Modalidad;
import com.if7100.repository.ModalidadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModalidadControllerTest {

    @Autowired
    private ModalidadRepository modalidadRepository;

    private Integer codigo = 1;

    private String titulo = "Golpes";

    private String descripcion = "Asesinada por golpes";

    private Modalidad modalidad = new Modalidad(2, "Disparo de bala", "Asesinada por disparo de bala");

    private  Modalidad modalidadConsultada = new Modalidad();

    @Test
    public void testUno() throws  Exception{
        modalidadRepository.save(modalidad);
    }

    @Test
    public void testDos() throws Exception{
        testUno();
        modalidadConsultada = modalidadRepository.findByCVTitulo(titulo);
        assertEquals(modalidadConsultada.getCVTitulo(), titulo);
        assertNotEquals(modalidadConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testTres() throws Exception{
        modalidadConsultada = modalidadRepository.findByCVTitulo(titulo);
        modalidadConsultada.setCVDescripcion(descripcion);
        modalidadRepository.save(modalidadConsultada);
        assertEquals(modalidadConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testCuatro() throws Exception{
        modalidadConsultada = modalidadRepository.findByCVTitulo(titulo);
        modalidadRepository.deleteById(modalidadConsultada.getCI_Codigo());
    }

}
