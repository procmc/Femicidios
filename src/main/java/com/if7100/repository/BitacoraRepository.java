/**
 * 
 */
package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Integer> {

	// Bitacora findByCVUsuario(String CV_DNI_Usuario);
	Bitacora findByCVUsuario(String CVUsuario);

	List<Bitacora> findByCodigoPais(Integer codigoPais);

}