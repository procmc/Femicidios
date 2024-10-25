package com.if7100.controller;

import com.if7100.entity.TipoLugar;
import com.if7100.repository.TipoLugarRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoLugarControllerTest {

    @Autowired
    private TipoLugarRepository tipoLugarRepository;

    private final String Titulo = "prueba 1";
    private final String Descripcion = "1";
    private TipoLugar tipoLugar;
    private TipoLugar tipoLugarConsultado;

    @BeforeAll
    public void setUp() {
        tipoLugar = new TipoLugar(Titulo, "Cambio");
    }

    @Test
    @Order(1)
    public void Test1_AgregartipoLugar() throws Exception {
        // Agrega un tipo de lugar
        tipoLugarRepository.save(tipoLugar);
    }

    @Test
    @Order(2)
    public void Test2_ConsultartipoLugar() throws Exception {
        // Busca un tipoLugar por título
        tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
        assertEquals(Titulo, tipoLugarConsultado.getCVTitulo());
        assertNotEquals(Descripcion, tipoLugarConsultado.getCVDescripcion()); // Verifica que la descripción es diferente
    }

    @Test
    @Order(3)
    public void Test3_ActualizartipoLugar() throws Exception {
        // Actualiza la descripción y verifica el cambio
        tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
        tipoLugarConsultado.setCVDescripcion(Descripcion);
        tipoLugarRepository.save(tipoLugarConsultado);

        tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
        assertEquals(Descripcion, tipoLugarConsultado.getCVDescripcion());
    }

    @Test
    @Order(4)
    public void Test4_EliminartipoLugar() throws Exception {
        // Elimina el tipoLugar por título
        tipoLugarConsultado = tipoLugarRepository.findByCVTitulo(Titulo);
        tipoLugarRepository.deleteById(tipoLugarConsultado.getCI_Codigo());
    }
}
