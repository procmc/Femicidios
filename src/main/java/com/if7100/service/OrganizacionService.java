package com.if7100.service;

import java.util.List;

import com.if7100.entity.Hecho;
import com.if7100.entity.Organizacion;
import com.if7100.entity.Paises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrganizacionService {

	List<Organizacion> findByCodigoPais(Integer codigoPais);

	List<Paises> getAllPaisesPage(Pageable pageable);

	List<Organizacion> getAllOrganizacion();

	Page<Organizacion> getAllOrganizacionPage(Pageable pageable);

	Organizacion saveOrganizacion(Organizacion organizacion);

	Organizacion getOrganizacionById(Integer Ci_Codigo_Organizacion);

	Organizacion updateOrganizacion(Organizacion organizacion);

	void deleteOrganizacionById(Integer Ci_Codigo_Organizacion);
}
