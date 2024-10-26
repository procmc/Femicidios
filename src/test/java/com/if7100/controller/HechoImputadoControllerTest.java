package com.if7100.controller;

import com.if7100.entity.HechoImputado;
import com.if7100.repository.HechoImputadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HechoImputadoControllerTest {

    @Autowired
    private HechoImputadoRepository hechoImputadoRepository;

    private final int CIHecho = 1;
    private final int CIImputado = 1;

    private HechoImputado hechoImputado;

    private List<HechoImputado> hechoImputadoConsultado;

   

    @BeforeAll
    public void setUp() {

        hechoImputado = new HechoImputado(CIHecho, CIImputado);

    }
    
    @Test
    @Order(1)
    public void testUno_GuardarHechoImputado() throws Exception {
        hechoImputadoRepository.save(hechoImputado);
    }

    @Test
    @Order(2)
    public void testDos_ConsultarHechoImputado() throws Exception {
        hechoImputadoConsultado = hechoImputadoRepository.findAllByCIHecho(CIHecho);
        boolean encontrado = false;
        for (HechoImputado hechoImputado1 : hechoImputadoConsultado) {
            encontrado = hechoImputado1.getCIImputado().equals(hechoImputado.getCIImputado()) && hechoImputado1.getCIHecho().equals(hechoImputado.getCIHecho());
            if (encontrado){
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    @Order(3)
    public void testTres_ActualizarHechoImputado() throws Exception {
        hechoImputadoConsultado = hechoImputadoRepository.findAllByCIHecho(CIHecho);
        boolean encontrado = false;
        for (HechoImputado hechoImputado1 : hechoImputadoConsultado) {
            encontrado = hechoImputado1.getCIImputado().equals(hechoImputado.getCIImputado()) && hechoImputado1.getCIHecho().equals(hechoImputado.getCIHecho());
            if (encontrado){
                hechoImputado1.setCIHecho(3);
                hechoImputadoRepository.save(hechoImputado1);
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    @Order(4)
    public void testCuatro_EliminarHechoImputado() throws Exception{
        hechoImputadoConsultado = hechoImputadoRepository.findAllByCIImputado(hechoImputado.getCIImputado());
        boolean encontrado = false;
        for (HechoImputado hechoImputado1 : hechoImputadoConsultado) {
            encontrado = hechoImputado1.getCIImputado().equals(hechoImputado.getCIImputado()) && hechoImputado1.getCIHecho().equals(hechoImputado.getCIHecho());
            if (encontrado){
                break;
            }
        }
        hechoImputadoRepository.deleteById(hechoImputado.getCI_Id());
    }


}
