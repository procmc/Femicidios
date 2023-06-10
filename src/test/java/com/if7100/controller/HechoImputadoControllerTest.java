package com.if7100.controller;

import com.if7100.entity.HechoImputado;
import com.if7100.repository.HechoImputadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HechoImputadoControllerTest {

    @Autowired
    private HechoImputadoRepository hechoImputadoRepository;

    private HechoImputado hechoImputado = new HechoImputado(1,1, 1);

    private List<HechoImputado> hechoImputadoConsultado;

    @Test
    public void testUno() throws Exception{
        hechoImputadoRepository.deleteById(hechoImputado.getCI_Id());
    }
    @Test
    public void testDos() throws Exception {
        hechoImputadoRepository.save(hechoImputado);
    }

    @Test
    public void testTres() throws Exception {
        hechoImputadoConsultado = hechoImputadoRepository.findAllByCIHecho(hechoImputado.getCIHecho());
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
    public void testCuatro() throws Exception {
        hechoImputadoConsultado = hechoImputadoRepository.findAllByCIImputado(hechoImputado.getCIImputado());
        boolean encontrado = false;
        for (HechoImputado hechoImputado1 : hechoImputadoConsultado) {
            encontrado = hechoImputado1.getCIImputado().equals(hechoImputado.getCIImputado()) && hechoImputado1.getCIHecho().equals(hechoImputado.getCIHecho());
            if (encontrado){
                break;
            }
        }
        assertTrue(encontrado);
    }



}
