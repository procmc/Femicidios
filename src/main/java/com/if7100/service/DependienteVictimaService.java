package com.if7100.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.if7100.entity.Dependiente;
import com.if7100.entity.DependienteVictima;
import org.springframework.data.domain.Pageable;

public interface DependienteVictimaService {

     Page<DependienteVictima> getAllDependienteVictimaPage(Pageable pageable);

    List<DependienteVictima> getAllDependienteVictima();

    DependienteVictima getDependienteVictimaById(Integer id);

    DependienteVictima saveDependienteVictima(DependienteVictima dependienteVictima);
    
    void deleteDependienteVictimaById(Integer id);

    void deleteByDependienteId(Integer dependienteId);

    public List<DependienteVictima> findBydependiente(Dependiente dependiente);

}
