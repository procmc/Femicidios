package com.if7100.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.if7100.entity.IdentidadGenero;

@Repository
public interface IdentidadGeneroRepository extends JpaRepository<IdentidadGenero, Integer>{

	//IdentidadGenero findByCedula(String cedula);

	@Query("SELECT ig FROM IdentidadGenero ig JOIN PaisesidentIdadesgeneros pig ON ig.Id = pig.identidadGenero.Id WHERE pig.pais.Id = :codigoPais")
    List<IdentidadGenero> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);


}

