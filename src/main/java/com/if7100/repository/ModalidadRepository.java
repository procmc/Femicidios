 package com.if7100.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.if7100.entity.Modalidad;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Integer>{
	
	Modalidad findByCVTitulo(String CV_Titulo);
}
