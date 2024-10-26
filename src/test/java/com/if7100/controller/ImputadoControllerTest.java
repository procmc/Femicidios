package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.Imputado;
import com.if7100.repository.HechoRepository;
import com.if7100.repository.ImputadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImputadoControllerTest {

    @Autowired
    private ImputadoRepository imputadoRepository;

    private String cV_DNI = "699";
    private String cv_nombre = "Fio";
    private String cv_genero = "Masculino";
    private String cv_Orientacion_Sexual = "Heterosexual";
    private char sexo = 'M';
    private String ci_edad = "24";
    private String CV_Lugar_Nacimiento = "Purgatorio";
    private String CV_Nacionalidad = "Costarricense";
    private String CVEducacion = "Primaria";
    private String CVOcupacion = "Carnicero";
    private String CVDomicilio = "Costa Rica";
    private String CVLugarResidencia = "Venezuela";
    private String CVCondicionMigratoria = "Regular";
    private String CVEtnia = "Negro";
    private String CVSituacionJuridica = "Fugitivo";
    private String CVEstadoConyugal = "NO";
    private String CVPermisoPortacionArmas = "NO";
    private String CVPertenenciaFuerzaSeguridad = "NO";
    private String CVAntecedentes = "SI";
    private String CVSuicidio = "NO";
    private String CVGenerador = "Organismo2";
    private int codigoPais = 52;

    Imputado imputado;

    Imputado imputadoConsultado;

    @BeforeAll
    public void setUp() {

        imputado = new Imputado(cV_DNI, cv_nombre, cv_genero, cv_Orientacion_Sexual, sexo, ci_edad, CV_Lugar_Nacimiento,
                CV_Nacionalidad, CVEducacion,
                CVOcupacion, CVDomicilio, CVLugarResidencia, CVCondicionMigratoria,
                CVEtnia, CVSituacionJuridica, CVEstadoConyugal, CVPermisoPortacionArmas,
                CVPertenenciaFuerzaSeguridad, CVAntecedentes, CVSuicidio, CVGenerador, codigoPais);

    }

    @Test
    @Order(1)
    public void testUno() throws Exception {
        imputadoRepository.save(imputado);

    }

    @Test
    @Order(2)
    public void testDos() throws Exception {
        imputadoConsultado = imputadoRepository.findByCVNombre(cv_nombre);
        assertEquals(cv_nombre, imputadoConsultado.getCVNombre());
        assertNotEquals(cv_genero, imputadoConsultado.getCVNombre());
    }

    @Test
    @Order(3)
    public void testTres() throws Exception {
        imputadoConsultado = imputadoRepository.findByCVNombre(cv_nombre);
        imputadoConsultado.setCVDni("120103");
        imputadoRepository.save(imputadoConsultado);

        imputadoConsultado = imputadoRepository.findByCVNombre(cv_nombre);
        assertNotEquals("12012103", imputadoConsultado.getCVDni());
    }

    @Test
    @Order(4)
    public void testCuatro() throws Exception {
        imputadoConsultado = imputadoRepository.findByCVNombre(cv_nombre);
        imputadoRepository.deleteById(imputadoConsultado.getCI_Id());
    }

}
