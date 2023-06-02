package com.if7100.service;


import java.util.List;

import com.if7100.entity.TipoVictima;

public interface TipoVictimaService {

    List<TipoVictima> getAllTipoVictimas();

    TipoVictima saveTipoVictima(TipoVictima tipoVictima);

    TipoVictima getTipoVictimaById(Integer Id);

    TipoVictima updateTipoVictima(TipoVictima tipoVictima);

    void deleteTipoVictimaById(Integer Id);

    TipoVictima getTipoVictimaByTitulo(String CVTitulo);

    TipoVictima getTipoVictimaByDescripcion(String CVDescripcion);

}
