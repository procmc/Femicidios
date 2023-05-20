package com.if7100.service;

import java.util.List;

import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Usuario;

/**
 * 
 * @author Michael Arauz Torrez
 * @since 21/04/2023
 */
public interface IdentidadGeneroService {
	List<IdentidadGenero> getAllIdentidadGenero();

	IdentidadGenero saveIdentidadGenero(IdentidadGenero identidadGenero);

	IdentidadGenero getIdentidadGeneroById(Integer Id);

	IdentidadGenero updateIdentidadGenero(IdentidadGenero identidadGenero);

	void deleteIdentidadGeneroById(Integer Id);
}
