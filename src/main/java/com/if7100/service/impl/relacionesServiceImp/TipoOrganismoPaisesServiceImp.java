package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.TipoOrganismo;
import com.if7100.entity.relacionesEntity.TipoOrganismoPaises;
import com.if7100.repository.relacionesRepository.TipoOrganismoPaisesRepository;
import com.if7100.service.relacionesService.TipoOrganismoPaisesService;

@Service
public class TipoOrganismoPaisesServiceImp implements TipoOrganismoPaisesService {

     @Autowired
    private TipoOrganismoPaisesRepository tipoOrganismoPaisesRepository;

    @Override
    public List<TipoOrganismoPaises> getAllRelaciones() {
        return tipoOrganismoPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        tipoOrganismoPaisesRepository.deleteById(id);
    }

    @Override
    public TipoOrganismoPaises saveTipoOrganismoPaises(TipoOrganismoPaises tipoOrganismoPaises) {
        return tipoOrganismoPaisesRepository.save(tipoOrganismoPaises);
    }

    @Override
    public List<TipoOrganismoPaises> getRelacionesByTipoOrganismo(TipoOrganismo tipoOrganismo) {
        return tipoOrganismoPaisesRepository.findByTipoOrganismo(tipoOrganismo);

    }
}
