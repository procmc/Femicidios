package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.relacionesEntity.NivelEducativoPaises;

public interface NivelEducativoPaisesService {
    
     List<NivelEducativoPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    NivelEducativoPaises saveNivelEducativoPaises(NivelEducativoPaises mivelEducativoPaises);

    List<NivelEducativoPaises> getRelacionesByNivelEducativo(NivelEducativo NivelEducativo);

}
