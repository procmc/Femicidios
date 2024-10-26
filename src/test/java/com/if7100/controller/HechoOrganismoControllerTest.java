package com.if7100.controller;

import com.if7100.entity.HechoOrganismo;
import com.if7100.repository.HechoOrganismoRepository;

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

public class HechoOrganismoControllerTest {

    @Autowired
    private HechoOrganismoRepository hechoOrganismoRepository;

    private HechoOrganismo hechoOrganismo; 

    private final int CIHecho = 10;
    private final int CIOrganismo = 2;

    private List<HechoOrganismo> hechoOrganismoConsultado;

    @BeforeAll
    public void setUp() {

        hechoOrganismo = new HechoOrganismo(CIHecho, CIOrganismo);
    }

    @Test
    @Order(1)
    public void testUno_GuardarHechoOrganismo() throws Exception {
        hechoOrganismoRepository.save(hechoOrganismo);
    }

    @Test
    @Order(2)
    public void testDos_ConsultarHechoOrganismo() throws Exception {
        hechoOrganismoConsultado = hechoOrganismoRepository.findAllByCIHecho(CIHecho);
        boolean encontrado = false;
        for (HechoOrganismo hechoOrganismo1 : hechoOrganismoConsultado) {
            encontrado = hechoOrganismo1.getCIOrganismo().equals(hechoOrganismo.getCIOrganismo()) && hechoOrganismo1.getCIHecho().equals(hechoOrganismo.getCIHecho());
            if (encontrado){
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    @Order(3)
    public void testTres_ActualizarHechoImputado() throws Exception {
        hechoOrganismoConsultado = hechoOrganismoRepository.findAllByCIHecho(CIHecho);
        boolean encontrado = false;
        for (HechoOrganismo hechoOrganismo1 : hechoOrganismoConsultado) {
            encontrado = hechoOrganismo1.getCIOrganismo().equals(hechoOrganismo.getCIOrganismo()) && hechoOrganismo1.getCIHecho().equals(hechoOrganismo.getCIHecho());
            if (encontrado){
                hechoOrganismo1.setCIHecho(1);
                hechoOrganismoRepository.save(hechoOrganismo1);
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    @Order(4)
    public void testCuatro_EliminarHechoImputado() throws Exception{
        hechoOrganismoConsultado = hechoOrganismoRepository.findAllByCIOrganismo(hechoOrganismo.getCIOrganismo());
        boolean encontrado = false;
        for (HechoOrganismo hechoOrganismo1 : hechoOrganismoConsultado) {
            encontrado = hechoOrganismo1.getCIOrganismo().equals(hechoOrganismo.getCIOrganismo()) && hechoOrganismo1.getCIHecho().equals(hechoOrganismo.getCIHecho());
            if (encontrado){
                break;
            }
        }
        hechoOrganismoRepository.deleteById(hechoOrganismo.getCI_Id());
    }
}
