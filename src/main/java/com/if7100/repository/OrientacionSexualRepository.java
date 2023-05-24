package com.if7100.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.OrientacionSexual;


@Repository
public interface OrientacionSexualRepository extends JpaRepository<OrientacionSexual, Integer> {
	 OrientacionSexual findByCVTitulo(String CV_Titulo);
}
 