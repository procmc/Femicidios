package com.if7100.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.if7100.entity.PaisesidentIdadesgeneros;
import com.if7100.repository.PaisesidentIdadesgenerosRepository;
import com.if7100.service.PaisesidentIdadesgenerosService;

@Service
public class PaisesidentIdadesgenerosServiceImpl implements PaisesidentIdadesgenerosService {

    private final PaisesidentIdadesgenerosRepository repository;

    public PaisesidentIdadesgenerosServiceImpl(PaisesidentIdadesgenerosRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(PaisesidentIdadesgeneros relacion) {
        repository.save(relacion);
    }

    @Override
    public List<PaisesidentIdadesgeneros> getAllRelaciones() {
        return repository.findAll();
    }

    @Override
    public void deleteRelacionById(Long id) {
        repository.deleteById(id);
    }
}
