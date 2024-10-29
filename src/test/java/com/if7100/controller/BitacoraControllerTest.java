package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.repository.BitacoraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BitacoraControllerTest {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    private final Integer CIId = 7;
    private final String CV_DNI_Usuario = "8";
    private final String nombreUsuario = "TisharyPrueba";
    private final String rol = "Convencional";
    private final String descripcion = "Elimino en Test";
    private final Date fecha = Date.valueOf("2024-10-25");

    private Bitacora bitacora;
    private  Bitacora bitacoraConsultada;

    @BeforeAll
    public void setUp() {
        bitacora = new Bitacora(CIId, CV_DNI_Usuario, rol, descripcion, fecha);
    }

    @Test
    @Order(1)
    public void testUno_GuardarBitacora() throws  Exception{
        System.out.println("Testsad "+bitacora.getCIIdBitacora());
        bitacoraRepository.save(bitacora);
    }

    @Test
    @Order(2)
    public void testDos_ConsultarBitacora() throws Exception{
        bitacoraConsultada = bitacoraRepository.findById(Integer.valueOf(bitacora.getCIIdBitacora())).get();
        assertEquals(CV_DNI_Usuario, bitacoraConsultada.getCVUsuario());
        assertNotEquals("Amini",bitacoraConsultada.getCVRol());
    }

    @Test
    @Order(3)
    public void testTres_ActualizarBitacora() throws Exception{
    	bitacoraConsultada = bitacoraRepository.findById(Integer.valueOf(bitacora.getCIIdBitacora())).get();
    	bitacoraConsultada.setCVRol("Administrador");
        bitacoraRepository.save(bitacoraConsultada);

        assertEquals("Administrador", bitacoraConsultada.getCVRol());
    }

    @Test
    @Order(4)
    public void testCuatro_EliminarBitacora() throws Exception{
        bitacoraConsultada = bitacoraRepository.findById(Integer.valueOf(bitacora.getCIIdBitacora())).get();
        bitacoraRepository.deleteById(bitacoraConsultada.getCIIdBitacora());
    }

}
