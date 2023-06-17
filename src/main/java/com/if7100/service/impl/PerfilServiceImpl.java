package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Perfil;
import com.if7100.repository.PerfilRepository;
import com.if7100.service.PerfilService;

/**
 * @author David
 *
 */
@Service
public class PerfilServiceImpl implements PerfilService{
	
	private PerfilRepository perfilRepository;
	
	public PerfilServiceImpl(PerfilRepository perfilRepository) {
		super();
		this.perfilRepository = perfilRepository;
	}

	@Override
	public Perfil savePerfil(Perfil perfil) {
		return perfilRepository.save(perfil);
	}

	@Override
	public List<Perfil> getAllPerfiles() {
		return perfilRepository.findAll();
	}

	@Override
	public Page<Perfil> getAllPerfilesPage(Pageable pageable){
		return perfilRepository.findAll(pageable);
	}

	@Override
	public Perfil getPerfilById(Integer id) {
		return perfilRepository.findById(id).get();
	}

	@Override
	public Perfil updatePerfil(Perfil perfil) {
		return perfilRepository.save(perfil);
	}

	@Override
	public void deletePerfilById(Integer id) {
		perfilRepository.deleteById(id);
		
	}
	
}
