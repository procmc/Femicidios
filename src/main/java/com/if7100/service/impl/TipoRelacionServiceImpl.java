package com.if7100.service.impl;

import com.if7100.entity.TipoRelacion;
import com.if7100.repository.TipoRelacionRepository;
import com.if7100.service.TipoRelacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoRelacionServiceImpl implements TipoRelacionService {

    private TipoRelacionRepository tipoRelacionRepository;

    public TipoRelacionServiceImpl(TipoRelacionRepository tipoRelacionRepository) {
        this.tipoRelacionRepository = tipoRelacionRepository;
    }

    @Override
    public List<TipoRelacion> getAllTipoRelaciones() {
        return tipoRelacionRepository.findAll();
    }

    @Override
    public TipoRelacion saveTipoRelacion(TipoRelacion tipoRelacion) {
        return tipoRelacionRepository.save(tipoRelacion);
    }

    @Override
    public TipoRelacion getTipoRelacionById(Integer Id) {
        return tipoRelacionRepository.findById(Id).get();
    }

    @Override
    public TipoRelacion updateTipoRelacion(TipoRelacion tipoRelacion) {
        return tipoRelacionRepository.save(tipoRelacion);
    }

    @Override
    public void deleteTipoRelacionById(Integer Id) {
        tipoRelacionRepository.deleteById(Id);
    }

    @Override
    public TipoRelacion getTipoRelacionByTitulo(String CVTitulo) {
        return tipoRelacionRepository.findByCVTitulo(CVTitulo);
    }

    @Override
    public TipoRelacion getTipoRelacionaByDescripcion(String CVDescripcion) {
        return tipoRelacionRepository.findByCVDescripcion(CVDescripcion);
    }
}
