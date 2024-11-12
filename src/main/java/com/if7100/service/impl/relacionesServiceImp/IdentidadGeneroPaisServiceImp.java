package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;
import org.springframework.stereotype.Service;

import com.if7100.entity.relacionesEntity.IdentidadGeneroPais;
import com.if7100.repository.relacionesRepository.IdentidadGeneroPaisRepository;
import com.if7100.service.relacionesService.IdentidadGeneroPaisService;

@Service
public class IdentidadGeneroPaisServiceImp implements IdentidadGeneroPaisService {

    private final IdentidadGeneroPaisRepository identidadGeneroPaisRepository;

    public IdentidadGeneroPaisServiceImp(IdentidadGeneroPaisRepository identidadGeneroPaisRepository) {
        this.identidadGeneroPaisRepository = identidadGeneroPaisRepository;
    }

    @Override
    public IdentidadGeneroPais saveIdentidadGeneroPais(IdentidadGeneroPais identidadGeneroPais) {
        return identidadGeneroPaisRepository.save(identidadGeneroPais);
    }

    @Override
    public List<IdentidadGeneroPais> getAllRelaciones() {
        return identidadGeneroPaisRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Long id) {
        identidadGeneroPaisRepository.deleteById(id);
    }

}
