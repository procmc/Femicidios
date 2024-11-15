package com.if7100.service.impl;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.SituacionJuridica;
import com.if7100.repository.SituacionJuridicaRepository;
import com.if7100.service.SituacionJuridicaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SituacionJuridicaServiceImpl implements SituacionJuridicaService {

    private SituacionJuridicaRepository situacionJuridicaRepository;

    public SituacionJuridicaServiceImpl(SituacionJuridicaRepository situacionJuridicaRepository) {
        this.situacionJuridicaRepository = situacionJuridicaRepository;
    }

    @Override
    public List<SituacionJuridica> getAllSituacionJuridica() {
        return situacionJuridicaRepository.findAll();
    }

    @Override
    public Page<SituacionJuridica> getAllSituacionJuridicaPage(Pageable pageable){
        return situacionJuridicaRepository.findAll(pageable);
    }

    @Override
    public SituacionJuridica saveSituacionJuridica(SituacionJuridica situacionJuridica) {
        return situacionJuridicaRepository.save(situacionJuridica);
    }

    @Override
    public SituacionJuridica getSituacionJuridicaById(Integer id) {
        return situacionJuridicaRepository.findById(id).get();
    }

    @Override
    public SituacionJuridica updateSituacionJuridica(SituacionJuridica situacionJuridica) {
        return situacionJuridicaRepository.save(situacionJuridica);
    }

    @Override
    public void deleteSituacionJuridicaById(Integer id) {
        situacionJuridicaRepository.deleteById(id);
    }

    @Override
    public SituacionJuridica getSituacionJuridicaByTitulo(String titulo) {
        return situacionJuridicaRepository.findByCVTitulo(titulo);
    }

    @Override
    public SituacionJuridica getSituacionJuridicaByDescripcion(String descripcion) {
        return situacionJuridicaRepository.findByCVDescripcion(descripcion);
    }

      //////
	public List<SituacionJuridica> getSituacionJuridicaByCodigoPais(Integer codigoPais) {
        return situacionJuridicaRepository.findAllByCodigoPais(codigoPais);
    }
}
