package com.if7100.service.impl;



import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.IdentidadGenero;
import com.if7100.repository.IdentidadGeneroRepository;
import com.if7100.service.IdentidadGeneroService;

/**
 * 
 * @author Michael Arauz Torrez
 * @since 21/04/2023
 */
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
