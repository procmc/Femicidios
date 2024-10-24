package com.if7100.controller;

import com.if7100.entity.Hecho;
import com.if7100.entity.Imputado;
import com.if7100.repository.HechoRepository;
import com.if7100.repository.ImputadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImputadoControllerTest {

    @Autowired
      private ImputadoRepository imputadoRepository;
    private int ci_id=2;
    private String cv_dni="699";
    private String cv_nombre="Kijan Acuña Medrano";
    private String cv_genero="Masculino";
    private String cv_Orientacion_Sexual="Heterosexual";
    private char sexo='M';
    private int ci_edad=24;
private String CV_Lugar_Nacimiento="Purgatorio";
private String cv_pais="Costa Rica";
private String CV_Nacionalidad="Costarricense";     
private String CV_Educacion="Primaria";  
private String CV_ocupacion="Carnicero";
private String CV_domicilio="Costa Rica"; 
private String CV_Lugar_Residencia="Venezuela";
private String CV_Condicion_Migratoria="Regular";
private String  CV_Etnia="Negro";
private String  CV_Situacion_Juridica="Fugitivo"; 
private String  CV_Estado_Conyugal="NO"; 
private String CV_Permiso_Portacion_Armas="NO";
private String CV_Pertenencia_Fuerza_Seguridad="NO";
private String  CV_Antecedentes="SI";
private String CV_Suicidio="NO";
private String CV_Generador="Organismo2";

    Imputado imputado = new Imputado("700260950", "MadaraKijan", "Masculino", "Hetero",sexo, "22", "BackTrack", 506);


    Imputado consultado= new Imputado();



    @Test
    public void testUno() throws Exception{
        imputadoRepository.save(imputado);

    }

    @Test
    public void testDos() throws Exception{
        consultado=imputadoRepository.findByCVNombre("MadaraKijan");
        assertEquals(consultado.getCVNombre(),"MadaraKijan");
        assertNotEquals(consultado.getCVNombre(),"Kijan");
    }

    @Test
    public void testTres() throws Exception{
        consultado=imputadoRepository.findByCVNombre("MadaraKijan");
        consultado.setCVNombre("KijanMadara");
        imputadoRepository.save(consultado);
        consultado=imputadoRepository.findByCVNombre("KijanMadara");
        assertNotEquals(consultado.getCVNombre(),"MadaraKijan");
    }

    @Test
    public void testCuatro() throws Exception{
        consultado = imputadoRepository.findByCVNombre("KijanMadara");
        imputadoRepository.delete(consultado);
    }
    
    @Test
    public void Test5() throws Exception {
    	
    	
    	consultado = imputadoRepository.findByCVNombre(cv_nombre);
    	consultado.setCVNombre(cv_nombre);
    	imputadoRepository.save(consultado);
    	consultado = imputadoRepository.findByCVNombre(cv_nombre);
    	 assertEquals(consultado.getCVNombre(), cv_nombre);
    	 assertEquals(consultado.getCVGenero(), cv_genero);
    	 assertEquals(consultado.getCVOrientacionSexual(), cv_Orientacion_Sexual);
    	 assertEquals(consultado.getCIEdad(), ci_edad);
    	 assertEquals(consultado.getCVLugarNacimiento(),CV_Lugar_Nacimiento );
    
    }

}
