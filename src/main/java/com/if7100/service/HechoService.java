package com.if7100.service;

import com.if7100.entity.Hecho;

import java.util.List;

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

}
