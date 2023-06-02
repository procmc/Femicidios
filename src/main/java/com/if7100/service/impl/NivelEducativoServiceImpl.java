package com.if7100.service.impl;

import com.if7100.entity.NivelEducativo;
import com.if7100.repository.NivelEducativoRepository;
import com.if7100.service.NivelEducativoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NivelEducativoServiceImpl implements NivelEducativoService {

    private NivelEducativoRepository nivelEducativoRepository;

    public NivelEducativoServiceImpl(NivelEducativoRepository nivelEducativoRepository){
        super();
        this.nivelEducativoRepository = nivelEducativoRepository;
    }
    @Override
    public List<NivelEducativo> getAllNivelEducativo() {
        return nivelEducativoRepository.findAll();
    }

    @Override
    public NivelEducativo saveNivelEducativo(NivelEducativo nivelEducativo) {
        return nivelEducativoRepository.save(nivelEducativo);
    }

    @Override
    public NivelEducativo getNivelEducativoById(Integer Id) {
        return nivelEducativoRepository.findById(Id).get();
    }

    @Override
    public NivelEducativo updateNivelEducativo(NivelEducativo nivelEducativo) {
        return nivelEducativoRepository.save(nivelEducativo);
    }

    @Override
    public void deleteNivelEducativoById(Integer Id) {
        nivelEducativoRepository.deleteById(Id);
    }

    @Override
    public NivelEducativo getNivelEducativoByTitulo(String CV_Titulo) {
        return nivelEducativoRepository.findByCVTitulo(CV_Titulo);
    }

    @Override
    public NivelEducativo getNivelEducativoByDescripcion(String CV_Descripcion) {
        return nivelEducativoRepository.findByCVDescripcion(CV_Descripcion);
    }
}
