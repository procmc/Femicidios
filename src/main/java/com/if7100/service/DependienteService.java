package com.if7100.service;

import java.util.List;

import com.if7100.entity.Dependiente;
import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.Victima;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DependienteService {


	List<Dependiente> getAllDependiente();

	Dependiente saveDependiente(Dependiente dependiente);

	Dependiente getDependienteById(Integer Id);

	List<TipoRelacionFamiliar> getAllTipoRelacionesFamilires();

	List<TipoRelacionFamiliar> getAllTipoRelacionesFamiliaresPage(Pageable pageable);

	List<Victima> getAllVictimas();

	List<Victima> getAllVictimasPage(Pageable pageable);

	void deleteDependienteById(Integer Id);

	Dependiente getDependienteByCVDNI(String CVDNI);

	Page<Dependiente> getAllDependientePage(Pageable pageable);

	List<Dependiente> getDependientesByCodigoPais(Integer codigoPais);

}
