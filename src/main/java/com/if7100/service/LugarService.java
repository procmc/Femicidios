package com.if7100.service;

import java.util.List;
import com.if7100.entity.Lugar;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.entity.TipoLugar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */

public interface LugarService {

     List<Lugar> getLugarByCodigoPaisUsuario(Integer codigoPaisUsuario);

    List<Lugar> getAllLugares(Integer CI_Hecho);

    List<Lugar> getAllLugar();
    List<TipoLugar> getAllTipoLugars();


    Lugar saveLugar(Lugar lugar);

    Lugar getLugarById(Integer Id);

    void deleteLugarById(Integer Id);

    Lugar updateLugar(Lugar lugar);

    Page<Lugar> getAllLugarPage(Pageable pageable);

    Page<Lugar> getAllLugaresPage(Pageable pageable, Integer CI_Hecho);
}
