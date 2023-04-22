package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoLugar; 

@Repository
public interface TipoLugarRepository extends JpaRepository<TipoLugar, Integer>{

	TipoLugar findByCVTitulo(String CVTitulo);
	
}
