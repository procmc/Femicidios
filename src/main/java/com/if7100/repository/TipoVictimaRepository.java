package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.if7100.entity.TipoVictima;




	@Repository
	public interface TipoVictimaRepository extends JpaRepository<TipoVictima, Integer> {
	//	TipoVictima findByCVTitulo(String CV_Titulo);
	}

