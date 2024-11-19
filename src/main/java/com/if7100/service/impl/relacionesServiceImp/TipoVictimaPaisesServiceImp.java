package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoVictima;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.entity.relacionesEntity.TipoVictimaPaises;
import com.if7100.repository.relacionesRepository.TipoRelacionPaisesRepository;
import com.if7100.repository.relacionesRepository.TipoVictimaPaisesRepository;
import com.if7100.service.relacionesService.TipoVictimaPaisesService;

@Service
public class TipoVictimaPaisesServiceImp implements TipoVictimaPaisesService {
    
        @Autowired
    private TipoVictimaPaisesRepository tipoVictimaPaisesRepository;

    @Override
    public List<TipoVictimaPaises> getAllRelaciones() {
        return tipoVictimaPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        tipoVictimaPaisesRepository.deleteById(id);
    }

    @Override
    public TipoVictimaPaises saveTipoVictimaPaises(TipoVictimaPaises tipoVictimaPaises) {
        return tipoVictimaPaisesRepository.save(tipoVictimaPaises);
    }

    @Override
    public List<TipoVictimaPaises> getRelacionesByTipoVictima(TipoVictima tipoVictima) {
        return tipoVictimaPaisesRepository.findByTipoVictima(tipoVictima);

    }
}
