package com.if7100.controller;

import com.if7100.entity.HechoOrganismo;
import com.if7100.repository.HechoOrganismoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class HechoOrganismoControllerTest {

    @Autowired
    private HechoOrganismoRepository hechoOrganismoRepository;

    private HechoOrganismo hechoOrganismo = new HechoOrganismo(1,1,1);

    private List<HechoOrganismo> hechoOrganismoConsultado;

    @Test
    public void testUno() throws Exception{
        hechoOrganismoRepository.deleteById(hechoOrganismo.getCI_Id());
    }

    @Test
    public void testDos() throws Exception{
        hechoOrganismoRepository.save(hechoOrganismo);
    }

    @Test
    public void testTres() throws Exception{
        hechoOrganismoConsultado = hechoOrganismoRepository.findAllByCIHecho(hechoOrganismo.getCIHecho());
        boolean encontrado = false;
        for (HechoOrganismo hechoOrganismo1 :
                hechoOrganismoConsultado) {
            encontrado = hechoOrganismo1.getCIOrganismo().equals(hechoOrganismo.getCIOrganismo()) && hechoOrganismo1.getCIHecho().equals(hechoOrganismo.getCIHecho());
            if (encontrado){
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    public void testCuatro() throws Exception{
        hechoOrganismoConsultado = hechoOrganismoRepository.findAllByCIOrganismo(hechoOrganismo.getCIOrganismo());
        boolean encontrado = false;
        for (HechoOrganismo hechoOrganismo1 :
                hechoOrganismoConsultado) {
            encontrado = hechoOrganismo1.getCIOrganismo().equals(hechoOrganismo.getCIOrganismo()) && hechoOrganismo1.getCIHecho().equals(hechoOrganismo.getCIHecho());
            if (encontrado){
                break;
            }
        }
        assertTrue(encontrado);
    }

}
