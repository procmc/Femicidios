package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.relacionesEntity.IdentidadGeneroPais;

public interface IdentidadGeneroPaisService {

    List<IdentidadGeneroPais> getAllRelaciones();

    void deleteRelacionById(Long id);

    IdentidadGeneroPais saveIdentidadGeneroPais(IdentidadGeneroPais identidadGeneroPais);

}
