package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.relacionesEntity.OrientacionesSexualesPaises;

public interface OrientacionesSexualesPaisesService {
    
    List<OrientacionesSexualesPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    OrientacionesSexualesPaises saveOrientacionesSexualesPaises(OrientacionesSexualesPaises orientacionesSexualesPaises);

    List<OrientacionesSexualesPaises> getRelacionesByOrientacionSexual(OrientacionSexual orientacionSexual);
}
