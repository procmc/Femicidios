package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Hecho;
import com.if7100.entity.ProcesoJudicial;

@Repository
public interface ProcesoJudicialRepository extends JpaRepository<ProcesoJudicial, Integer> {

	//@Query("SELECT DISTINCT h.procesoJudicial FROM Hecho h WHERE h.codigoPais = :codigoPais")
	//List<ProcesoJudicial> findProcesosByCodigoPais(@Param("codigoPais") Integer codigoPais);

	List<ProcesoJudicial> findProcesoJudicialByCICodigoPais(Integer codigoPais);

	ProcesoJudicial findByCVEstado(String CVEstado);
	

}
