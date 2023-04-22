/**
 * 
 */

package com.if7100.service.impl;

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
	public TipoLugar getTipoLugarByCVTitulo(String CVTitulo){
	   return tipoLugarRepository.findByCVTitulo(CVTitulo);
	}
 	
	
	
}
