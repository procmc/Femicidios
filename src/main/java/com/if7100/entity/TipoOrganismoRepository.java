package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoOrganismo; 

@Repository
public interface TipoOrganismoRepository extends JpaRepository<TipoOrganismo, Integer>{

	TipoOrganismo findByCVTitulo(String CVTitulo);
	
}