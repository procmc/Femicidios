/**
 * 
 */
package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.if7100.entity.Bitacora;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Integer>{
	
	//Bitacora findByCVUsuario(String CV_DNI_Usuario);
	Bitacora findByCVUsuario(String CVUsuario);
	
	
}