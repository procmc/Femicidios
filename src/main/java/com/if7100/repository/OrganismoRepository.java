package com.if7100.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Organismo;


@Repository
public interface OrganismoRepository extends JpaRepository<Organismo, Integer> {
	Organismo findByCVNombre(String CV_Nombre);
}
