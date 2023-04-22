package com.if7100.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Imputado;


@Repository
public interface ImputadoRepository extends JpaRepository<Imputado, Integer> {
	Imputado findByCVNombre(String CV_Nombre);
}
