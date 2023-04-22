package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Lugar;

@Repository

public interface LugarRepository extends JpaRepository<Lugar, Integer> {
	@Query("SELECT p FROM Lugar p WHERE CAST(p.CIF_DNI_Victima AS string) LIKE %?1%" 
			+ " OR CAST(p.CIF_Tipo_Lugar AS string) LIKE %?1%"
			+ " OR CAST(p.CV_Ciudad AS string) LIKE %?1%"
			+ " OR CAST(p.CV_Pais AS string) LIKE %?1%"
	)
	 List<Lugar> findAll(String palabraClave);
}
