/**
 * 
 */
package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Dependiente;

import com.if7100.repository.DependienteRepository;

import com.if7100.service.DependienteService;

/**
 * @author Hadji
 *
 */


@Service
public class DependienteServiceImpl implements DependienteService{
	
	private DependienteRepository dependienteRepository;
	
	public DependienteServiceImpl ( DependienteRepository dependienteRepository)
	{
		super();
		this.dependienteRepository =dependienteRepository;
	}
	
	@Override
	public List<Dependiente> getAllDependiente(){
		return dependienteRepository.findAll(); 
	}

	@Override
	public Page<Dependiente> getAllDependientePage(Pageable pageable){
		return dependienteRepository.findAll(pageable);
	}

	@Override
	public Dependiente saveDependiente(Dependiente dependiente){
		return dependienteRepository.save(dependiente); 
	}
	
		@Override
	public Dependiente getDependienteById(Integer Id){
		return dependienteRepository.findById(Id).get(); 
	}
	
	@Override
	public Dependiente updateDependiente (Dependiente dependiente){
		return dependienteRepository.save(dependiente); 
	}
	
	@Override
	public void deleteDependienteById(Integer Id){
		dependienteRepository.deleteById(Id); 
	}
	
	@Override
	public Dependiente getDependienteByCVDNI(String CVNombre){
		return dependienteRepository.findByCVDNI(CVNombre); 
	}
	
	

}
