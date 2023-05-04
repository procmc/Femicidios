package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.repository.HechoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HechoControllerTest {

    @Autowired
    private HechoRepository hechoRepository;

    private Integer pais = 506;

    private Integer victima = 1;

    private Integer relacion = 1;

    private Integer modalidad = 1;

    private Hecho hecho = new Hecho(pais, 3, 3, 3);

    private Hecho hechoConsultado = new Hecho();

    @Test
    public void testUno() throws Exception{
        hechoRepository.save(hecho);
    }

    @Test
    public void testDos() throws Exception{
        testUno();
        hechoConsultado = hechoRepository.findByCIPais(pais);
        assertEquals(hechoConsultado.getCIPais(), pais);
        assertNotEquals(hechoConsultado.getCITipoVictima(), victima);
        assertNotEquals(hechoConsultado.getCITipoRelacion(), relacion);
        assertNotEquals(hechoConsultado.getCIModalidad(), modalidad);
    }

    @Test
    public void testTres() throws Exception{
        hechoConsultado = hechoRepository.findByCIPais(pais);
        hechoConsultado.setCITipoVictima(victima);
        hechoConsultado.setCITipoRelacion(relacion);
        hechoConsultado.setCIModalidad(modalidad);
        hechoRepository.save(hechoConsultado);
        hechoConsultado = hechoRepository.findByCIPais(pais);
        assertEquals(hechoConsultado.getCITipoVictima(), victima);
    }

    @Test
    public void testCuatro() throws Exception{
        hechoConsultado = hechoRepository.findByCIPais(pais);
        hechoRepository.deleteById(hechoConsultado.getCI_Id());
    }

}
