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

    private Integer codigo = 1;

    private String titulo = "Domicilio Víctima";

    private String descripcion = "Lugar de la residencia particular de la víctima";

    private TipoLugar tipoLugar = new TipoLugar("Domicilio Víctimario", "Lugar de residencia particular del víctimario");

    private TipoLugar tipoLugarConsultada = new TipoLugar();

    @Test
    public void testUno() throws  Exception{
        tipoLugarRepository.save(tipoLugar);
    }

    @Test
    public void testDos() throws Exception{
        testUno();
        tipoLugarConsultada = tipoLugarRepository.findByCVTitulo(titulo);
        assertEquals(tipoLugarConsultada.getCVTitulo(), titulo);
        assertNotEquals(tipoLugarConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testTres() throws Exception{
        tipoLugarConsultada = tipoLugarRepository.findByCVTitulo(titulo);
        tipoLugarConsultada.setCVDescripcion(descripcion);
        tipoLugarRepository.save(tipoLugarConsultada);
        assertEquals(tipoLugarConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testCuatro() throws Exception{
        tipoLugarConsultada = tipoLugarRepository.findByCVTitulo(titulo);
        tipoLugarRepository.deleteById(tipoLugarConsultada.getCI_Codigo());
    }

}
