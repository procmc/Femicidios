package com.if7100.service;

import java.util.List;
import com.if7100.entity.Lugar;
/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */

public interface LugarService {

    List<Lugar> getAllLugares(Integer CI_Hecho);

    List<Lugar> getAllLugar();

    Lugar saveLugar(Lugar lugar);

    Lugar getLugarById(Integer Id);

    void deleteLugarById(Integer Id);

    Lugar updateLugar(Lugar lugar);

}
