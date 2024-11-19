package com.if7100.service.impl;

import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoVictima;
import com.if7100.repository.TipoVictimaRepository;
import com.if7100.service.TipoVictimaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoVictimaServiceImpl implements TipoVictimaService {

    private TipoVictimaRepository tipoVictimaRepository;

    public TipoVictimaServiceImpl(TipoVictimaRepository tipoVictimaRepository) {
        super();
        this.tipoVictimaRepository = tipoVictimaRepository;
    }

    @Override
    public List<TipoVictima> getAllTipoVictimas() {
        return tipoVictimaRepository.findAll();
    }

    @Override
    public Page<TipoVictima> getAllTipoVictimasPage(Pageable pageable){
        return tipoVictimaRepository.findAll(pageable);
    }

    @Override
    public TipoVictima saveTipoVictima(TipoVictima tipoVictima) {
        return tipoVictimaRepository.save(tipoVictima);
    }

    @Override
    public TipoVictima getTipoVictimaById(Integer Id) {
        return tipoVictimaRepository.findById(Id).get();
    }

    @Override
    public TipoVictima updateTipoVictima(TipoVictima tipoVictima) {
        return tipoVictimaRepository.save(tipoVictima);
    }

    @Override
    public void deleteTipoVictimaById(Integer Id) {
    tipoVictimaRepository.deleteById(Id);
    }

    @Override
    public TipoVictima getTipoVictimaByTitulo(String CVTitulo) {
        return tipoVictimaRepository.findByCVTitulo(CVTitulo);
    }

    @Override
    public TipoVictima getTipoVictimaByDescripcion(String CVDescripcion) {
        return tipoVictimaRepository.findByCVDescripcion(CVDescripcion);
    }

         //////
	public List<TipoVictima> getTipoVictimaByCodigoPais(Integer codigoPais) {
        return tipoVictimaRepository.findAllByCodigoPais(codigoPais);
    }
}
