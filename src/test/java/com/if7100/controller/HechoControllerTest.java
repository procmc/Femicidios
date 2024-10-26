package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.entity.Victima;
import com.if7100.repository.HechoRepository;
import com.if7100.repository.ProcesoJudicialRepository;
import com.if7100.repository.VictimaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
public class HechoControllerTest {

    @Autowired
    private HechoRepository hechoRepository;
    @Autowired
    private VictimaRepository victimaRepository;
    @Autowired
    private ProcesoJudicialRepository procesoJudicialRepository;

    private final int CITipoVictima = 1;
    private final int CITipoRelacion = 1;
    private final int CIModalidad = 1;
    private final Victima victima = new Victima("203450876", "Albertina", "Chill", "Pepper", 30, 2, "Cartago", 3,
            "Costarricense", 2,
            "Cruz Rojista", "Limon centro", "Limon", "Ninguna", "Todo bien", "Mestizo", "Ninguna", "Ninguna", 0,
            "Nada", 2);

    Date fecha = Date.valueOf("2024-10-25");
    private final ProcesoJudicial procesoJudicial = new ProcesoJudicial("Abierto", fecha, 1, "Ninguna", "Ninguna");

    private final String CVAgresionSexual = "No";
    private final String CVDenunciaPrevia = "No";
    private final int CIIdGenerador = 1;
    private final String CDFecha = "2021-10-10";
    private final String CVDetalles = "Detalles";
    private final int codigoPais = 51;

    private Hecho hecho;
    private Hecho hechoConsultado;

    @BeforeAll
    public void setUp() {

        victimaRepository.save(victima); // Guardar la víctima primero
        procesoJudicialRepository.save(procesoJudicial); // Guardar el proceso judicial primero

        hecho = new Hecho(CITipoVictima, CITipoRelacion, CIModalidad, procesoJudicial, victima, CVAgresionSexual,
                CVDenunciaPrevia,
                CIIdGenerador, CDFecha, CVDetalles, codigoPais);

    }

    @Test
    @Order(1)
    public void testUno_GuardarHecho() throws Exception {
        hechoRepository.save(hecho);
    }

    @Test
    @Order(2)
    public void testDos_ConsultarHecho() throws Exception {
        hechoConsultado = hechoRepository.findByCVDenunciaPrevia(CVDenunciaPrevia);
        assertEquals(codigoPais, hechoConsultado.getCodigoPais());
        assertNotEquals(CITipoVictima, hechoConsultado.getCodigoPais());
    }

    @Test
    @Order(3)
    public void testTres_ActualizarHecho() throws Exception {
        hechoConsultado = hechoRepository.findByCVDenunciaPrevia(CVDenunciaPrevia);
        hechoConsultado.setCodigoPais(51);
        hechoRepository.save(hechoConsultado);

        hechoConsultado = hechoRepository.findByCVDenunciaPrevia(CVDenunciaPrevia);
        assertNotEquals(52, hechoConsultado.getCodigoPais());
    }

    @Test
    @Order(4)
    public void testCuatro() throws Exception {

        hechoConsultado = hechoRepository.findByCVDenunciaPrevia(CVDenunciaPrevia);
        hechoRepository.deleteById(hechoConsultado.getCI_Id());
    }

}
