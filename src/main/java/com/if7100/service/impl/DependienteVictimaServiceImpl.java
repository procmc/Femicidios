package com.if7100.service.impl;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.if7100.entity.Dependiente;
import com.if7100.entity.DependienteVictima;
import com.if7100.repository.DependienteVictimaRepository;
import com.if7100.service.DependienteVictimaService;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;

@Service
public class DependienteVictimaServiceImpl implements DependienteVictimaService {

    private final DependienteVictimaRepository dependienteVictimaRepository;

    public DependienteVictimaServiceImpl(DependienteVictimaRepository dependienteVictimaRepository) {
        this.dependienteVictimaRepository = dependienteVictimaRepository;
    }


    @Override
    public Page<DependienteVictima> getAllDependienteVictimaPage(Pageable pageable) {
        return dependienteVictimaRepository.findAll(pageable);
    }

    @Override
    public List<DependienteVictima> getAllDependienteVictima() {
        return dependienteVictimaRepository.findAll();
    }

    @Override
    public DependienteVictima getDependienteVictimaById(Integer id) {
        return dependienteVictimaRepository.findById(id).orElse(null);
    }

    @Override
    public DependienteVictima saveDependienteVictima(DependienteVictima dependienteVictima) {
        return dependienteVictimaRepository.save(dependienteVictima);
    }

    @Override
    public void deleteDependienteVictimaById(Integer id) {
        dependienteVictimaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByDependienteId(Integer dependienteId) {
        dependienteVictimaRepository.deleteByDependienteId(dependienteId);
    }

    public List<DependienteVictima> findBydependiente(Dependiente dependiente) {
        return dependienteVictimaRepository.findBydependiente(dependiente);
    }
}
