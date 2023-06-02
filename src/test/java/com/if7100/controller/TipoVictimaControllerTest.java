package com.if7100.controller;
import com.if7100.entity.Modalidad;
import com.if7100.entity.TipoVictima;
import com.if7100.repository.TipoVictimaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TipoVictimaControllerTest {

    @Autowired
    private TipoVictimaRepository tipoVictimaRepository;

    private Integer codigo = 1;

    private String titulo = "Principal";

    private String descripcion = "La victima principal del feminicidio";

    private TipoVictima tipoVictima = new TipoVictima(2, "Vinculado", "Victima vinculada al crimen principal");

    private  TipoVictima tipoVictimaConsultada = new TipoVictima();

    @Test
    public void testUno() throws  Exception{
        tipoVictimaRepository.save(tipoVictima);
    }

    @Test
    public void testDos() throws Exception{
        //testUno();
        tipoVictimaConsultada = tipoVictimaRepository.findByCVTitulo(titulo);
        assertEquals(tipoVictimaConsultada.getCVTitulo(), titulo);
        assertNotEquals(tipoVictimaConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testTres() throws Exception{
        tipoVictimaConsultada = tipoVictimaRepository.findByCVTitulo(titulo);
        tipoVictimaConsultada.setCVDescripcion(descripcion);
        tipoVictimaRepository.save(tipoVictimaConsultada);
        assertEquals(tipoVictimaConsultada.getCVDescripcion(), descripcion);
    }

    @Test
    public void testCuatro() throws Exception{
        tipoVictimaConsultada = tipoVictimaRepository.findByCVTitulo(titulo);
        tipoVictimaRepository.deleteById(tipoVictimaConsultada.getCI_Codigo());
    }

}
