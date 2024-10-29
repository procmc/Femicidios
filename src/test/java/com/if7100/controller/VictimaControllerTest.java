package com.if7100.controller;


import com.if7100.entity.Victima;
import com.if7100.repository.VictimaRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.aspectj.lang.annotation.Before;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.sql.Date;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VictimaControllerTest {

    @Autowired
    private VictimaRepository victimaRepository;

    private String CVDNI = "02345";
    private String CVNombre = "AlbertinaB";
    private String CVApellidoPaterno = "Chill";
    private String CVApellidoMaterno = "Pepper";
    private int CVEdad = 30;
    private int CVGenero = 2;
    private String CVLugarNac = "Cartago";
    private int CVOrientaSex = 3;
    private String nacionalidad = "Costarricense";
    private int educacion = 2;
    private String profesion = "Cruz Rojista";
    private String domicilio = "Limon centro";
    private String CVLugarResidencia = "Limon";
    private String discapacidad = "Ninguna";
    private String CVCondicionMigratoria = "Ninguna";
    private String etnia = "Blanca";
    private String CVMedidasProteccion = "Ninguna";
    private String CVDenunciasPrevias = "No";
    private int CIHijos = 0;
    private String CVGenerador = "Nada";    
    private int codigopais = 52;

    private Victima victima;
    private Victima victimaConsultada = new Victima();

    @BeforeAll
    public void setUp() {
        victima = new Victima(CVDNI, CVNombre, CVApellidoPaterno, CVApellidoMaterno, CVEdad, CVGenero, CVLugarNac,
                CVOrientaSex, nacionalidad, educacion, profesion, domicilio, CVLugarResidencia, discapacidad,
                CVCondicionMigratoria, etnia, CVMedidasProteccion, CVDenunciasPrevias, CIHijos, CVGenerador, codigopais);
    }
    @Test
    @Order(1)
    public void Test1_GuardarVictima() throws Exception {
        victimaRepository.save(victima);
    }

    @Test
    @Order(2)
    public void Test2_ConsultarVictima() throws Exception {

        victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
        assertEquals(CVDNI, victimaConsultada.getCVDNI());
        assertNotEquals("Maria", victimaConsultada.getCVNombre());
        
    }


    @Test
    @Order(3)
    public void Test3_ActualizarVictima() throws Exception {

        victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
        victimaConsultada.setCICodigoPais(51);
        victimaRepository.save(victimaConsultada);

        assertEquals(51, victimaConsultada.getCICodigoPais());
        
    }

    @Test
    public void Test4_EliminarVictima() throws Exception {
        victimaConsultada = victimaRepository.findByCVNombre(CVNombre);
        victimaRepository.deleteById(victimaConsultada.getCI_Id());
    }

}
