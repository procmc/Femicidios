package com.if7100.controller;


import com.if7100.entity.Dependiente;
import com.if7100.repository.DependienteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DependienteControllerTest {

    private String CVDNI = "203450876";
    private int Codigo = 2;
    private int tipoRelacion = 3;

    private Dependiente dependiente = new Dependiente(Codigo,CVDNI,tipoRelacion);

    @Autowired
    private DependienteRepository dependienteRepository;


    private Dependiente dependienteConsultada = new Dependiente();

    @Test
    public void Test1() throws Exception {

        dependienteRepository.save(dependiente);

    }

    @Test
    public void Test2() throws Exception {

        dependienteConsultada = dependienteRepository.findByCVDNI(CVDNI);
        assertEquals(dependienteConsultada.getCVDNI(), CVDNI);
        assertNotEquals(dependienteConsultada.getCI_Codigo(), this.Codigo);
        assertNotEquals(dependienteConsultada.getCI_Tiporelacion(),this.tipoRelacion);

    }


    @Test
    public void Test3() throws Exception {

    	 dependienteConsultada = dependienteRepository.findByCVDNI(CVDNI);
        dependienteConsultada.setCVDNI(CVDNI);;
        dependienteRepository.save(dependienteConsultada);
        dependienteConsultada = dependienteRepository.findByCVDNI(CVDNI);
        assertEquals(dependienteConsultada.getCI_Codigo(), this.Codigo);
        assertEquals(dependienteConsultada.getCI_Tiporelacion(),this.tipoRelacion);

    }



}
