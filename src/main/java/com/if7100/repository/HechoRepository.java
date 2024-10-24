package com.if7100.repository;

import com.if7100.entity.Hecho;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.entity.Victima;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HechoRepository extends JpaRepository<Hecho, Integer> {

    //probando

    Hecho findByCITipoVictima(Integer CITipoVictima);

    Hecho findByCITipoRelacion(Integer CITipoRelacion);

    Hecho findByCIModalidad(Integer CIModalidad);

    //Hecho findByCIIdVictima(Integer CIIdVictima);
    List<Hecho> findByVictima(Victima victima);

    //Hecho findByCIIdProceso(Integer CIIdProceso);
    List<Hecho> findByProcesoJudicial(ProcesoJudicial procesoJudicial);

    Hecho findByCVAgresionSexual(String CVAgresionSexual);

    Hecho findByCVDenunciaPrevia(String CVDenunciaPrevia);

    Hecho findByCIIdGenerador(Integer CIIdGenerador);

    Hecho findByCDFecha(String CDFecha);


    //Muestra los hechos segun el codigo de pais del hecho
    List<Hecho> findByCodigoPais(Integer codigoPais);

}
