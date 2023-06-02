package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.Imputado;
import com.if7100.repository.HechoRepository;
import com.if7100.repository.ImputadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImputadoControllerTest {

    @Autowired
    private ImputadoRepository imputadoRepository;

    Imputado imputado = new Imputado("700260950", "MadaraKijan", "Masculino", "Hetero", "22", "BackTrack", "506");


    Imputado consultado= new Imputado();



    @Test
    public void testUno() throws Exception{
        imputadoRepository.save(imputado);

    }

    @Test
    public void testDos() throws Exception{
        consultado=imputadoRepository.findByCVNombre("MadaraKijan");
        assertEquals(consultado.getCVNombre(),"MadaraKijan");
        assertNotEquals(consultado.getCVNombre(),"Kijan");
    }

    @Test
    public void testTres() throws Exception{
        consultado=imputadoRepository.findByCVNombre("MadaraKijan");
        consultado.setCVNombre("KijanMadara");
        imputadoRepository.save(consultado);
        consultado=imputadoRepository.findByCVNombre("KijanMadara");
        assertNotEquals(consultado.getCVNombre(),"MadaraKijan");
    }

    @Test
    public void testCuatro() throws Exception{
        consultado = imputadoRepository.findByCVNombre("KijanMadara");
        imputadoRepository.delete(consultado);
    }

}
