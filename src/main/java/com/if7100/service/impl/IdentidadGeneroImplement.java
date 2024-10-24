package com.if7100.service.impl;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.IdentidadGenero;
import com.if7100.repository.IdentidadGeneroRepository;
import com.if7100.service.IdentidadGeneroService;

@Service
public class IdentidadGeneroImplement implements IdentidadGeneroService {

	private IdentidadGeneroRepository identidadGeneroRepository;

	public IdentidadGeneroImplement(IdentidadGeneroRepository identidadGeneroRepository) {
		super();
		this.identidadGeneroRepository = identidadGeneroRepository;
	}

	@Override
	public List<IdentidadGenero> getAllIdentidadGenero() {
		// TODO Auto-generated method stub
		return identidadGeneroRepository.findAll();
	}

	@Override
	public Page<IdentidadGenero> getAllIdentidadGeneroPage(Pageable pageable){
		return identidadGeneroRepository.findAll(pageable);
	}

	@Override
	public IdentidadGenero saveIdentidadGenero(IdentidadGenero identidadGenero) {
	
		return identidadGeneroRepository.save(identidadGenero);
	}

	@Override
	public IdentidadGenero getIdentidadGeneroById(Integer Id) {
		return identidadGeneroRepository.findById(Id).get();
	}

	@Override
	public IdentidadGenero updateIdentidadGenero(IdentidadGenero identidadGenero) {
		// TODO Auto-generated method stub
		return identidadGeneroRepository.save(identidadGenero);
	}

	@Override
	public void deleteIdentidadGeneroById(Integer Id) {
		identidadGeneroRepository.deleteById(Id);
		
	}

}
