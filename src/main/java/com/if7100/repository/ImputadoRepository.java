package com.if7100.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Imputado;
import com.if7100.entity.Usuario;


@Repository
public interface ImputadoRepository extends JpaRepository<Imputado, Integer> {
	Imputado findByCVNombre(String CV_Nombre);

		 //usuario por codigo de pais
	 List<Imputado> findByCodigoPais(Integer codigoPais);
}
