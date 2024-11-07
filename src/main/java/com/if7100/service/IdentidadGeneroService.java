package com.if7100.service;

import java.util.List;

import com.if7100.entity.IdentidadGenero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IdentidadGeneroService {
	List<IdentidadGenero> getAllIdentidadGenero();

	Page<IdentidadGenero> getAllIdentidadGeneroPage(Pageable pageable);

	IdentidadGenero saveIdentidadGenero(IdentidadGenero identidadGenero);

	IdentidadGenero getIdentidadGeneroById(Integer Id);

	IdentidadGenero updateIdentidadGenero(IdentidadGenero identidadGenero);
	

	void deleteIdentidadGeneroById(Integer Id);

	List<IdentidadGenero> getIdentidadesGeneroByCodigoPais(Integer codigoPais);
}