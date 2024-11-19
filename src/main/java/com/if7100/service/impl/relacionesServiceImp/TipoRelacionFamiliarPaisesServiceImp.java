package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.relacionesEntity.TipoRelacionFamiliarPaises;
import com.if7100.repository.relacionesRepository.TipoRelacionFamiliarPaisesRepository;
import com.if7100.service.relacionesService.TipoRelacionFamiliarPaisesService;

@Service
public class TipoRelacionFamiliarPaisesServiceImp implements TipoRelacionFamiliarPaisesService {
    
    @Autowired
    private TipoRelacionFamiliarPaisesRepository tipoRelacionFamiliarPaisesRepository;

    @Override
    public List<TipoRelacionFamiliarPaises> getAllRelaciones() {
        return tipoRelacionFamiliarPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        tipoRelacionFamiliarPaisesRepository.deleteById(id);
    }

    @Override
    public TipoRelacionFamiliarPaises saveTipoRelacionFamiliarPaises(TipoRelacionFamiliarPaises tipoRelacionFamiliarPaises) {
        return tipoRelacionFamiliarPaisesRepository.save(tipoRelacionFamiliarPaises);
    }

    @Override
    public List<TipoRelacionFamiliarPaises> getRelacionesByTipoRelacionFamiliar(TipoRelacionFamiliar tipoRelacionFamiliar) {
        return tipoRelacionFamiliarPaisesRepository.findByTipoRelacionFamiliar(tipoRelacionFamiliar);

    }
    
}
