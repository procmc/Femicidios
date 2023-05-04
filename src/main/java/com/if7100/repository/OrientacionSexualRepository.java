package com.if7100.repository;


import com.if7100.entity.OrientacionSexual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrientacionSexualRepository extends JpaRepository<OrientacionSexual, Integer> {
	 OrientacionSexual findByCVTitulo(String CVTitulo);

	 OrientacionSexual findByCVDescripcion(String CVDescripcion);

}
 