package com.if7100.service.impl;

import com.if7100.entity.TipoRelacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;// para el implements

import java.util.List;
import com.if7100.service.TipoLugarService;
import com.if7100.entity.TipoLugar;
import com.if7100.repository.TipoLugarRepository;

/**
 * @author kendall B
 * Fecha: 11 de abril del 2023
 */

@Service
public class TipoLugarServiceImpl implements TipoLugarService{

    private TipoLugarRepository tipoLugarRepository;

    public TipoLugarServiceImpl (TipoLugarRepository tipoLugarRepository) {
        super();
        this.tipoLugarRepository = tipoLugarRepository;
    }

    @Override
    public List<TipoLugar> getAllTipoLugares(){
        return tipoLugarRepository.findAll();
    }

    @Override
    public Page<TipoLugar> getAllTipoLugaresPage(Pageable pageable){
        return tipoLugarRepository.findAll(pageable);
    }

    @Override
    public TipoLugar saveTipoLugar(TipoLugar tipoLugar){
        return tipoLugarRepository.save(tipoLugar);
    }

    @Override
    public TipoLugar getTipoLugarByCodigo(Integer Codigo){
        return tipoLugarRepository.findById(Codigo).get();
    }

    @Override
    public TipoLugar updateTipoLugar(TipoLugar tipoLugar){
        return tipoLugarRepository.save(tipoLugar);
    }

    @Override
    public void deleteTipoLugarByCodigo(Integer Codigo){
        tipoLugarRepository.deleteById(Codigo);
    }

    @Override
    public TipoLugar getTipoLugarByTitulo(String CVTitulo) {
        return tipoLugarRepository.findByCVTitulo(CVTitulo);
    }

    @Override
    public TipoLugar getTipoLugarByDescripcion(String CVDescripcion) {
        return tipoLugarRepository.findByCVDescripcion(CVDescripcion);
    }



}
