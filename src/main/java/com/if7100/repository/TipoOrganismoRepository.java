package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoOrganismo;

@Repository
public interface TipoOrganismoRepository extends JpaRepository<TipoOrganismo, Integer>{

	TipoOrganismo findByCVTitulo(String CVTitulo);
	
	@Query("SELECT tor FROM TipoOrganismo tor JOIN TipoOrganismoPaises top ON tor.CI_Codigo = top.tipoOrganismo.CI_Codigo WHERE top.pais.Id = :codigoPais")
    List<TipoOrganismo> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);


}