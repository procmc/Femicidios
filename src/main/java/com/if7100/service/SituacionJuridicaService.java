package com.if7100.service;

import com.if7100.entity.SituacionJuridica;

import java.util.List;

public interface SituacionJuridicaService {

    List<SituacionJuridica> getAllSituacionJuridica();

    SituacionJuridica saveSituacionJuridica(SituacionJuridica situacionJuridica);

    SituacionJuridica getSituacionJuridicaById(Integer id);

    SituacionJuridica updateSituacionJuridica(SituacionJuridica situacionJuridica);

    void deleteSituacionJuridicaById(Integer id);

    SituacionJuridica getSituacionJuridicaByTitulo(String titulo);

    SituacionJuridica getSituacionJuridicaByDescripcion(String descripcion);

}
