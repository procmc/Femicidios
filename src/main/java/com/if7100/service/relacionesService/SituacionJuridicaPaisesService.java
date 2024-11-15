package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.SituacionJuridica;
import com.if7100.entity.relacionesEntity.SituacionJuridicaPaises;

public interface SituacionJuridicaPaisesService {
    
    List<SituacionJuridicaPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    SituacionJuridicaPaises saveSituacionJuridicaPaises(SituacionJuridicaPaises situacionJuridicaPaises);

    List<SituacionJuridicaPaises> getRelacionesBySituacionJuridica(SituacionJuridica situacionJuridica);

}
