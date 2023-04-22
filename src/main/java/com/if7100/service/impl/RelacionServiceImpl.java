package com.if7100.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.Relacion;
import com.if7100.repository.RelacionRepository;
import com.if7100.service.RelacionService;

@Service
public class RelacionServiceImpl implements RelacionService{
	
	private RelacionRepository relacionRepository;
	
	
	
	public RelacionServiceImpl(RelacionRepository relacionRepository) {
		super();
		this.relacionRepository = relacionRepository;
	}

	
	@Override
	public List<Relacion> getAllTypeRelaciones() {
		// TODO Auto-generated method stub
		return relacionRepository.findAll();
	}

	@Override
	public Relacion saveRelacion(Relacion typerelacion) {
		// TODO Auto-generated method stub
		return relacionRepository.save(typerelacion);
	}

	@Override
	public Relacion getRelacionById(Integer Id) {
		// TODO Auto-generated method stub
		return relacionRepository.findById(Id).get();
	}

	@Override
	public Relacion updateRelacion(Relacion relacion) {
		// TODO Auto-generated method stub
		return relacionRepository.save(relacion);
	}

	@Override
	public void deleteRelacionById(Integer Id) {
		// TODO Auto-generated method stub
		relacionRepository.deleteById(Id);
	}

	@Override
	public Relacion getRelacionByCVNombr(String CVtitulo) {
		// TODO Auto-generated method stub
		return relacionRepository.findByCVtitulo(CVtitulo);
	}

}
