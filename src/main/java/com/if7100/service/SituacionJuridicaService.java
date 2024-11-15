package com.if7100.service;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.SituacionJuridica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SituacionJuridicaService {

    List<SituacionJuridica> getAllSituacionJuridica();

    Page<SituacionJuridica> getAllSituacionJuridicaPage(Pageable pageable);

    SituacionJuridica saveSituacionJuridica(SituacionJuridica situacionJuridica);

    SituacionJuridica getSituacionJuridicaById(Integer id);

    SituacionJuridica updateSituacionJuridica(SituacionJuridica situacionJuridica);

    void deleteSituacionJuridicaById(Integer id);

    SituacionJuridica getSituacionJuridicaByTitulo(String titulo);

    SituacionJuridica getSituacionJuridicaByDescripcion(String descripcion);

    List<SituacionJuridica> getSituacionJuridicaByCodigoPais(Integer codigoPais);

}
