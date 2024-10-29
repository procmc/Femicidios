package com.if7100.service;

import com.if7100.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface HechoService {

    List<Hecho> getAllHechos();

    List<Paises> getAllPaisesPage(Pageable pageable);

    Page<Hecho> getAllHechosPage(Pageable pageable);

    List<Modalidad> getAllModalidades();

    List<Modalidad> getAllModalidadesPage(Pageable pageable);

    List<TipoRelacion> getAllTipoRelaciones();

    List<TipoRelacion> getAllTipoRelacionesPage(Pageable pageable);

    List<TipoVictima> getAllTipoVictimas();

    List<TipoVictima> getAllTipoVictimasPage(Pageable pageable);

    List<Organismo> getAllOrganismos();

    List<Organismo> getAllOrganismosPage(Pageable pageable);

    List<Victima> getAllVictimas();

    List<Victima> getAllVictimasPage(Pageable pageable);

    List<ProcesoJudicial> getAllProcesosJudiciales();

    List<ProcesoJudicial> getAllProcesosJudicialesPage(Pageable pageable);

    Hecho saveHecho(Hecho hecho);

    Hecho getHechoById(Integer Id);

    Hecho updateHecho(Hecho hecho);

    void deleteHechoById(Integer Id);

    Hecho getHechoByTipoVictima(Integer CITipoVictima);

    Hecho getHechoByTipoRelacion(Integer CITipoRelacion);

    Hecho getHechoByModalidad(Integer CIModalidad);

    //List<Hecho> getHechoByCIIdVictima(Integer CIIdVictima);
    List<Hecho> getHechoByVictima(Victima victima);

    //Hecho getHechoByCIIdProceso(Integer CIIdProceso);
    List<Hecho> getHechoByProcesoJudicial(ProcesoJudicial procesoJudicial);

    Hecho getHechoByCVAgresionSexual(String CVAgresionSexual);

    Hecho getHechoByCVDenunciaPrevia(String CVDenunciaPrevia);

    Hecho getHechoByCIIdGenerador(Integer CIIdGenerador);

    Hecho getHechoByCDFecha(String CDFecha);

    



	List<Hecho> findByCodigoPais(Integer codigoPais);


}
