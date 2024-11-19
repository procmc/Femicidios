package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.relacionesEntity.TipoRelacionFamiliarPaises;

public interface TipoRelacionFamiliarPaisesService {
    
    List<TipoRelacionFamiliarPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    TipoRelacionFamiliarPaises saveTipoRelacionFamiliarPaises(TipoRelacionFamiliarPaises tipoRelacionFamiliarPaises);

    List<TipoRelacionFamiliarPaises> getRelacionesByTipoRelacionFamiliar(TipoRelacionFamiliar tipoRelacionFamiliar);
}
