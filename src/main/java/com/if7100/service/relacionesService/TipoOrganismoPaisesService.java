package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.TipoOrganismo;
import com.if7100.entity.relacionesEntity.TipoOrganismoPaises;

public interface TipoOrganismoPaisesService {
    
    List<TipoOrganismoPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    TipoOrganismoPaises saveTipoOrganismoPaises(TipoOrganismoPaises tipoOrganismoPaises);

    List<TipoOrganismoPaises> getRelacionesByTipoOrganismo(TipoOrganismo tipoOrganismo);
}
