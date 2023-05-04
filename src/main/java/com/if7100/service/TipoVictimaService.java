package com.if7100.service;


import com.if7100.entity.TipoVictima;

import java.util.List;

public interface TipoVictimaService {

    List<TipoVictima> getAllTipoVictimas();

    TipoVictima saveTipoVictima(TipoVictima tipoVictima);

    TipoVictima getTipoVictimaById(Integer Id);

    TipoVictima updateTipoVictima(TipoVictima tipoVictima);

    void deleteTipoVictimaById(Integer Id);

    TipoVictima getTipoVictimaByTitulo(String CVTitulo);

    TipoVictima getTipoVictimaByDescripcion(String CVDescripcion);

}
