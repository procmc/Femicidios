package com.if7100.controller;

import com.if7100.entity.SituacionJuridica;
import com.if7100.repository.SituacionJuridicaRepository;
import com.if7100.service.SituacionJuridicaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SituacionJuridicaControllerTest {

    @Autowired
    private SituacionJuridicaRepository situacionJuridicaRepository;

    @Autowired
    private SituacionJuridicaService situacionJuridicaService;

    @Test
    public void testUno() throws Exception{

//        Pageable pageRequest = PageRequest.of(0,2);
//        Page<SituacionJuridica> situacionJuridicaPages = situacionJuridicaRepository.findAll(pageRequest);
//        System.out.println(situacionJuridicaPages.getTotalPages());
//        do {
//            situacionJuridicaPages = situacionJuridicaRepository.findAll(pageRequest);
//            for (SituacionJuridica situacionJuridica :
//                    situacionJuridicaPages.getContent()) {
//                System.out.println(situacionJuridica.getCI_Codigo());
//            }
//            pageRequest = pageRequest.next();
//        } while (!situacionJuridicaPages.isLast());

        int numeroPagina = 0; // Número de página actual (0 para la primera página)
        int elementosPorPagina = 10; // Número máximo de elementos por página
        int paginasDeseadas = 5; // Número deseado de páginas

        Pageable pageable = PageRequest.of(numeroPagina, elementosPorPagina);

        Page<SituacionJuridica> situacionJuridicaPage = situacionJuridicaRepository.findAll(pageable);

// Ajustar el tamaño de página para garantizar 5 páginas
        long numeroTotalElementos = situacionJuridicaPage.getTotalElements();
        int tamanoPagina = (int) Math.ceil(numeroTotalElementos / (double) paginasDeseadas);

        pageable = PageRequest.of(numeroPagina, tamanoPagina);

        situacionJuridicaPage = situacionJuridicaRepository.findAll(pageable);
        System.out.println(situacionJuridicaPage.getTotalPages());
        for (SituacionJuridica situacionJuridica :
                situacionJuridicaPage.getContent()) {
            System.out.println(situacionJuridica.getCI_Codigo());
        }

    }

}
