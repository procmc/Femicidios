package com.if7100.service.impl;

import com.if7100.entity.Hecho;
import com.if7100.repository.HechoRepository;
import com.if7100.service.HechoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HechoServiceImpl implements HechoService {

    private HechoRepository hechoRepository;

    public HechoServiceImpl(HechoRepository hechoRepository) {
        super();
        this.hechoRepository = hechoRepository;
    }


    @Override
    public List<Hecho> getAllHechos() {
        return hechoRepository.findAll();
    }

    @Override
    public Hecho saveHecho(Hecho hecho) {
        return hechoRepository.save(hecho);
    }

    @Override
    public Hecho getHechoById(Integer Id) {
        return hechoRepository.findById(Id).get();
    }

    @Override
    public Hecho updateHecho(Hecho hecho) {
        return hechoRepository.save(hecho);
    }

    @Override
    public void deleteHechoById(Integer Id) {
        hechoRepository.deleteById(Id);
    }

    @Override
    public Hecho getHechoByPais(Integer CIPais) {
        return hechoRepository.findByCIPais(CIPais);
    }

    @Override
    public Hecho getHechoByTipoVictima(Integer CITipoVictima) {
        return hechoRepository.findByCITipoVictima(CITipoVictima);
    }

    @Override
    public Hecho getHechoByTipoRelacion(Integer CITipoRelacion) {
        return hechoRepository.findByCITipoRelacion(CITipoRelacion);
    }

    @Override
    public Hecho getHechoByModalidad(Integer CIModalidad) {
        return hechoRepository.findByCIModalidad(CIModalidad);
    }
}
