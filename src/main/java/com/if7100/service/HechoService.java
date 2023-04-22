package com.if7100.service;

import com.if7100.entity.Hecho;

import java.util.List;

public interface HechoService {

    List<Hecho> getAllHechos();

    Hecho saveHecho(Hecho hecho);

    Hecho getHechoById(Integer Id);

    Hecho updateHecho(Hecho hecho);

    void deleteHechoById(Integer Id);

    Hecho getHechoByLugar(Integer CVLugar);

    Hecho getHechoByTipoVictima(Integer CVTipoVictima);

    Hecho getHechoByTipoRelacion(Integer CVTipoRelacion);

    Hecho getHechoByModalidad(Integer CVModalidad);

}
