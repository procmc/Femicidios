package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.repository.TipoRelacionFamiliarRepository;
import com.if7100.service.TipoRelacionFamiliarService;

@Service
public class TipoRelacionFamiliarServImpl implements TipoRelacionFamiliarService {
	private TipoRelacionFamiliarRepository tipoRelacionFamiliarRepository;
	
	public TipoRelacionFamiliarServImpl(TipoRelacionFamiliarRepository tipoRelacionFamiliarRepository) {
		super();
		this.tipoRelacionFamiliarRepository = tipoRelacionFamiliarRepository;
	}

	@Override
	public List<TipoRelacionFamiliar> getAllTipoRelacionFamiliar() {
		
		return tipoRelacionFamiliarRepository.findAll();
	}

	@Override
	public Page<TipoRelacionFamiliar> getAllTipoRelacionFamiliar(Pageable pageable) {

		return tipoRelacionFamiliarRepository.findAll(pageable);
	}

	@Override
	public TipoRelacionFamiliar saveTipoRelacionFamiliar(TipoRelacionFamiliar identidadGenero) {
		// TODO Auto-generated method stub
		return tipoRelacionFamiliarRepository.save(identidadGenero);
	}

	@Override
	public TipoRelacionFamiliar getTipoRelacionFamiliarById(Integer Id) {
		// TODO Auto-generated method stub
		return tipoRelacionFamiliarRepository.findById(Id).get();
	}

	@Override
	public TipoRelacionFamiliar updateTipoRelacionFamiliar(TipoRelacionFamiliar tiporelacion) {
		// TODO Auto-generated method stub
		return tipoRelacionFamiliarRepository.save(tiporelacion);
	}

	@Override
	public void deleteTipoRelacionFamiliarById(Integer Id) {
		// TODO Auto-generated method stub
		tipoRelacionFamiliarRepository.deleteById(Id);
	}

	     //////
	public List<TipoRelacionFamiliar> getTipoRelacionFamiliarByCodigoPais(Integer codigoPais) {
        return tipoRelacionFamiliarRepository.findAllByCodigoPais(codigoPais);
    }
}
