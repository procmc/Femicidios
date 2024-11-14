package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.relacionesEntity.OrientacionesSexualesPaises;
import com.if7100.repository.relacionesRepository.OrientacionesSexualesPaisesRepository;
import com.if7100.service.relacionesService.OrientacionesSexualesPaisesService;

@Service
public class OrientacionesSexualesPaisesServiceImp implements OrientacionesSexualesPaisesService {
    
      private final OrientacionesSexualesPaisesRepository orientacionesSexualesPaisesRepository;

    public OrientacionesSexualesPaisesServiceImp(OrientacionesSexualesPaisesRepository orientacionesSexualesPaisesRepository) {
        this.orientacionesSexualesPaisesRepository = orientacionesSexualesPaisesRepository;
    }

    @Override
    public List<OrientacionesSexualesPaises> getAllRelaciones() {
        return orientacionesSexualesPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        orientacionesSexualesPaisesRepository.deleteById(id);

    }

    @Override
    public OrientacionesSexualesPaises saveOrientacionesSexualesPaises(
            OrientacionesSexualesPaises orientacionesSexualesPaises) {
        return orientacionesSexualesPaisesRepository.save(orientacionesSexualesPaises);
    }

    @Override
    public List<OrientacionesSexualesPaises> getRelacionesByOrientacionSexual(OrientacionSexual orientacionSexual) {
        return orientacionesSexualesPaisesRepository.findByOrientacionSexual(orientacionSexual);
    }

    
}
