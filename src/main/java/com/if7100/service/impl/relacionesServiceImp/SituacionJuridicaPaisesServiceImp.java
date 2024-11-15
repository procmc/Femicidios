package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.SituacionJuridica;
import com.if7100.entity.relacionesEntity.SituacionJuridicaPaises;
import com.if7100.repository.relacionesRepository.SituacionJuridicaPaisesRepository;
import com.if7100.service.relacionesService.SituacionJuridicaPaisesService;

@Service
public class SituacionJuridicaPaisesServiceImp implements SituacionJuridicaPaisesService {

    private final SituacionJuridicaPaisesRepository situacionJuridicaPaisesRepository;

    public SituacionJuridicaPaisesServiceImp(SituacionJuridicaPaisesRepository situacionJuridicaPaisesRepository) {
        this.situacionJuridicaPaisesRepository = situacionJuridicaPaisesRepository;
    }

    @Override
    public List<SituacionJuridicaPaises> getAllRelaciones() {
        return situacionJuridicaPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        situacionJuridicaPaisesRepository.deleteById(id);
    }

    @Override
    public SituacionJuridicaPaises saveSituacionJuridicaPaises(
            SituacionJuridicaPaises situacionJuridicaPaises) {
        return situacionJuridicaPaisesRepository.save(situacionJuridicaPaises);
    }

    @Override
    public List<SituacionJuridicaPaises> getRelacionesBySituacionJuridica(SituacionJuridica situacionJuridica) {
        return situacionJuridicaPaisesRepository.findBySituacionJuridica(situacionJuridica);
    }
    
}
