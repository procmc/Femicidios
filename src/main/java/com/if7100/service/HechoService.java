package com.if7100.service;

import java.util.List;

import com.if7100.entity.Hecho;

public interface HechoService {

    List<Hecho> getAllHechos();

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

}
