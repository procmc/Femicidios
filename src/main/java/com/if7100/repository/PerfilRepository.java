package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.if7100.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{
	Perfil findByCVDescripcion(String CV_Descripcion);
}
