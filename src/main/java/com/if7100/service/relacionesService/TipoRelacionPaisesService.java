package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.TipoRelacion;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;

public interface TipoRelacionPaisesService {
    
     List<TipoRelacionPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    TipoRelacionPaises saveTipoRelacionPaises(TipoRelacionPaises tipoRelacionPaises);

    List<TipoRelacionPaises> getRelacionesByTipoRelacion(TipoRelacion tipoRelacion);
}
