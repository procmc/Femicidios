package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.TipoRelacion;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.repository.relacionesRepository.TipoRelacionPaisesRepository;
import com.if7100.service.relacionesService.TipoRelacionPaisesService;

@Service
public class TipoRelacionPaisesServiceImp implements TipoRelacionPaisesService {
    
    @Autowired
    private TipoRelacionPaisesRepository tipoRelacionPaisesRepository;

    @Override
    public List<TipoRelacionPaises> getAllRelaciones() {
        return tipoRelacionPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        tipoRelacionPaisesRepository.deleteById(id);
    }

    @Override
    public TipoRelacionPaises saveTipoRelacionPaises(TipoRelacionPaises tipoRelacionPaises) {
        return tipoRelacionPaisesRepository.save(tipoRelacionPaises);
    }

    @Override
    public List<TipoRelacionPaises> getRelacionesByTipoRelacion(TipoRelacion tipoRelacion) {
        return tipoRelacionPaisesRepository.findByTipoRelacion(tipoRelacion);

    }
}
