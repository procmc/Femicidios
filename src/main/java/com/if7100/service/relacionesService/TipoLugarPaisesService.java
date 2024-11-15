package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.TipoLugar;
import com.if7100.entity.relacionesEntity.TipoLugarPaises;


public interface TipoLugarPaisesService {
    
    List<TipoLugarPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    TipoLugarPaises saveTipoLugarPaises(TipoLugarPaises tipoLugarPaises);

    List<TipoLugarPaises> getRelacionesByTipoLugar(TipoLugar tipoLugar);

}
