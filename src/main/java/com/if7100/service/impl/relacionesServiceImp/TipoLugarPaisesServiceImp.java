package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.TipoLugar;
import com.if7100.entity.relacionesEntity.TipoLugarPaises;
import com.if7100.repository.relacionesRepository.TipoLugarPaisesRepository;
import com.if7100.service.relacionesService.TipoLugarPaisesService;

@Service
public class TipoLugarPaisesServiceImp implements TipoLugarPaisesService {
    
    @Autowired
    private TipoLugarPaisesRepository tipoLugarPaisesRepository;

    @Override
    public List<TipoLugarPaises> getAllRelaciones() {
        return tipoLugarPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        tipoLugarPaisesRepository.deleteById(id);
    }

    @Override
    public TipoLugarPaises saveTipoLugarPaises(TipoLugarPaises tipoLugarPaises) {
        return tipoLugarPaisesRepository.save(tipoLugarPaises);
    }

    @Override
    public List<TipoLugarPaises> getRelacionesByTipoLugar(TipoLugar tipoLugar) {
        return tipoLugarPaisesRepository.findByTipoLugar(tipoLugar);

    }
}
