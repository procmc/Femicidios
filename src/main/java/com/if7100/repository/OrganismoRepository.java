package com.if7100.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Organismo;


@Repository
public interface OrganismoRepository extends JpaRepository<Organismo, Integer> {
	Organismo findByCVNombre(String CV_Nombre);

	 List<Organismo> findByCodigoPais(Integer codigoPais);
}
