package com.if7100.controller;

import com.if7100.entity.Bitacora;

import com.if7100.repository.BitacoraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BitacoraControllerTest {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    private Integer idUsuario = 1;

    private String nombreUsuario = "TisharyPrueba";
    
    private String rol = "AdministradoraPrueba";

    private String descripcion = "Elimino en Test";
    private Date fechaEliminacion;

    private Bitacora bitacora = new Bitacora(2,"MelaniePrueba","Convencional","Elimino en TestInsertado",fechaEliminacion);

    private  Bitacora bitacoraConsultada = new Bitacora();

    @Test
    public void testUno() throws  Exception{
        bitacoraRepository.save(bitacora);
    }

    @Test
    public void testDos() throws Exception{
        testUno();
        bitacoraConsultada = bitacoraRepository.findByCVUsuario(nombreUsuario);
        assertEquals(bitacoraConsultada.getCVUsuario(), nombreUsuario);
        assertNotEquals(bitacoraConsultada.getCVRol(), rol);
    }

    @Test
    public void testTres() throws Exception{
    	bitacoraConsultada = bitacoraRepository.findByCVUsuario(nombreUsuario);
    	bitacoraConsultada.setCVRol(rol);
        bitacoraRepository.save(bitacoraConsultada);
        assertEquals(bitacoraConsultada.getCVRol(), rol);
    }

    @Test
    public void testCuatro() throws Exception{
        bitacoraConsultada = bitacoraRepository.findByCVUsuario(nombreUsuario);
        bitacoraRepository.deleteById(bitacoraConsultada.getCIIdBitacora());
    }

}
