package com.if7100.service;

import java.util.List;

import com.if7100.entity.Dependiente;
import com.if7100.entity.TipoRelacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DependienteService
{
		
	List<Dependiente> getAllDependiente();
	
	Dependiente saveDependiente(Dependiente dependiente);
	
	Dependiente getDependienteById(Integer Id);
	
	Dependiente updateDependiente(Dependiente dependiente);
	
	 List<TipoRelacion> getAllTipoRelaciones();

	  List<TipoRelacion> getAllTipoRelacionesPage(Pageable pageable);
	
	void deleteDependienteById(Integer Id);

	Dependiente getDependienteByCVDNI(String CVDNI);

    Page<Dependiente> getAllDependientePage(Pageable pageable);
}
