package com.if7100.repository;

import com.if7100.entity.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Integer> {

    List<Lugar> findByCIHecho(Integer CI_Hecho);

	/*@Query("SELECT p FROM Lugar p WHERE CAST(p.CI_Hecho AS string) LIKE %?1%"
			+ " OR CAST(p.CI_Tipo_Lugar AS string) LIKE %?1%"
			+ " OR CAST(p.CV_Ciudad AS string) LIKE %?1%"
			+ " OR CAST(p.CI_Pais AS string) LIKE %?1%"
	)
	 List<Lugar> findAll(String palabraClave);*/

    //Lugar findByCI_Hecho(Integer CI_Hecho);
}
