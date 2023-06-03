package com.if7100.service;

import com.if7100.entity.*;

import java.util.List;

public interface HechoService {

    List<Hecho> getAllHechos();

    List<Modalidad> getAllModalidades();

    List<TipoRelacion> getAllTipoRelaciones();

    List<TipoVictima> getAllTipoVictimas();

    List<Organismo> getAllOrganismos();

    List<Victima> getAllVictimas();

    List<ProcesoJudicial> getAllProcesosJudiciales();

    Hecho saveHecho(Hecho hecho);

    Hecho getHechoById(Integer Id);

    Hecho updateHecho(Hecho hecho);

    void deleteHechoById(Integer Id);

    Hecho getHechoByPais(Integer CVPais);

    Hecho getHechoByTipoVictima(Integer CITipoVictima);

    Hecho getHechoByTipoRelacion(Integer CITipoRelacion);

    Hecho getHechoByModalidad(Integer CIModalidad);

    Hecho getHechoByCIIdVictima(Integer CIIdVictima);

    Hecho getHechoByCIIdProceso(Integer CIIdProceso);

    Hecho getHechoByCVAgresionSexual(String CVAgresionSexual);

    Hecho getHechoByCVDenunciaPrevia(String CVDenunciaPrevia);

    Hecho getHechoByCIIdGenerador(Integer CIIdGenerador);

    Hecho getHechoByCDFecha(String CDFecha);

}
