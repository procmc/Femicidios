package com.if7100.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.OrientacionSexual;


@Repository
public interface OrientacionSexualRepository extends JpaRepository<OrientacionSexual, Integer> {
	 OrientacionSexual findByCVTitulo(String CV_Titulo);

	 @Query("SELECT os FROM OrientacionSexual os JOIN OrientacionesSexualesPaises osp ON os.CI_Codigo = osp.orientacionSexual.CI_Codigo WHERE osp.pais.Id = :codigoPais")
    List<OrientacionSexual> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);

}
 