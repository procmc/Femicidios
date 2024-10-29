package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Victima;

@Repository
public interface VictimaRepository extends JpaRepository<Victima, Integer> { 

	Victima findByCVNombre(String CV_Nombre);

	 //obtener las victimas a partir del pais del hecho al que se relaciona
	 List<Victima> findByHechos_CodigoPais(Integer codigoPais);

	 List<Victima> findVictimasByCICodigoPais(Integer codigoPais);

}
