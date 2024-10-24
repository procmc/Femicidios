package com.if7100.service;


import com.if7100.entity.TipoVictima;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoVictimaService {

    List<TipoVictima> getAllTipoVictimas();

    TipoVictima saveTipoVictima(TipoVictima tipoVictima);

    TipoVictima getTipoVictimaById(Integer Id);

    TipoVictima updateTipoVictima(TipoVictima tipoVictima);

    void deleteTipoVictimaById(Integer Id);

    TipoVictima getTipoVictimaByTitulo(String CVTitulo);

    TipoVictima getTipoVictimaByDescripcion(String CVDescripcion);

    Page<TipoVictima> getAllTipoVictimasPage(Pageable pageable);
}
