package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.TipoVictima;
import com.if7100.entity.relacionesEntity.TipoVictimaPaises;

public interface TipoVictimaPaisesService {
    
    List<TipoVictimaPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    TipoVictimaPaises saveTipoVictimaPaises(TipoVictimaPaises tipoVictimaPaises);

    List<TipoVictimaPaises> getRelacionesByTipoVictima(TipoVictima tipoVictima);
}
