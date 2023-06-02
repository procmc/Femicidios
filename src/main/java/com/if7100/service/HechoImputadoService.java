package com.if7100.service;

import com.if7100.entity.HechoImputado;

import java.util.List;

public interface HechoImputadoService {

    List<HechoImputado> getAllHechoImputado();

    List<HechoImputado> getAllHechosImputado(Integer CI_Hecho);

    List<HechoImputado> getAllHechoImputados(Integer CI_Imputado);

    HechoImputado saveHechoImputado(HechoImputado hechoImputado);

    HechoImputado getHechoImputadoById(Integer Id);

    HechoImputado updateHechoImputado(HechoImputado hechoImputado);

    void deleteHechoImputadoById(Integer Id);

    HechoImputado getHechoImputadoByIdHecho(Integer CIHecho);

    HechoImputado getHechoImputadoByIdImputado(Integer CIImputado);

}
