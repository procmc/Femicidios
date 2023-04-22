package com.if7100.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.ProcesoJudicial;


@Repository
public interface ProcesoJudicialRepository extends JpaRepository<ProcesoJudicial, Integer> {
	ProcesoJudicial findByCIDenunciante(int CI_Denunciante);
}
