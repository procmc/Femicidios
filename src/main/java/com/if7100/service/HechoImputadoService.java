package com.if7100.service;

import com.if7100.entity.HechoImputado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HechoImputadoService {

    List<HechoImputado> getAllHechoImputado();

    Page<HechoImputado> getAllHechoImputadoPage(Pageable pageable);

    Page<HechoImputado> getAllHechosImputadoPage(Pageable pageable, Integer CI_Hecho);

    Page<HechoImputado> getAllHechoImputadosPage(Pageable pageable, Integer CI_Imputado);

    List<HechoImputado> getAllHechosImputado(Integer CI_Hecho);

    List<HechoImputado> getAllHechoImputados(Integer CI_Imputado);

    HechoImputado saveHechoImputado(HechoImputado hechoImputado);

    HechoImputado getHechoImputadoById(Integer Id);

    HechoImputado updateHechoImputado(HechoImputado hechoImputado);

    void deleteHechoImputadoById(Integer Id);

    List<HechoImputado> getHechoImputadoByIdHecho(Integer CIHecho);

    List<HechoImputado> getHechoImputadoByIdImputado(Integer CIImputado);

}
