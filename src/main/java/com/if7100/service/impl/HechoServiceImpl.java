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
    public Hecho getHechoByLugar(Integer CVLugar) {
        return hechoRepository.findByCVLugar(CVLugar);
    }

    @Override
    public Hecho getHechoByTipoVictima(Integer CVTipoVictima) {
        return hechoRepository.findByCVTipoVictima(CVTipoVictima);
    }

    @Override
    public Hecho getHechoByTipoRelacion(Integer CVTipoRelacion) {
        return hechoRepository.findByCVTipoRelacion(CVTipoRelacion);
    }

    @Override
    public Hecho getHechoByModalidad(Integer CVModalidad) {
        return hechoRepository.findByCVModalidad(CVModalidad);
    }
}
