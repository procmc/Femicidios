package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.relacionesEntity.NivelEducativoPaises;
import com.if7100.repository.relacionesRepository.NivelEducativoPaisesRepository;
import com.if7100.service.relacionesService.NivelEducativoPaisesService;

@Service
public class NivelEducativoPaisesServiceImp implements NivelEducativoPaisesService {

    private final NivelEducativoPaisesRepository nivelEducativoPaisesRepository;

    public NivelEducativoPaisesServiceImp(NivelEducativoPaisesRepository nivelEducativoPaisesRepository) {
        this.nivelEducativoPaisesRepository = nivelEducativoPaisesRepository;
    }

    @Override
    public List<NivelEducativoPaises> getAllRelaciones() {
        return nivelEducativoPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        nivelEducativoPaisesRepository.deleteById(id);
    }

    @Override
    public NivelEducativoPaises saveNivelEducativoPaises(
            NivelEducativoPaises nivelEducativoPaises) {
        return nivelEducativoPaisesRepository.save(nivelEducativoPaises);
    }

    @Override
    public List<NivelEducativoPaises> getRelacionesByNivelEducativo(NivelEducativo nivelEducativo) {
        return nivelEducativoPaisesRepository.findByNivelEducativo(nivelEducativo);
    }
    
}
