package com.if7100.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.if7100.entity.IdentidadGenero;
@Repository
public interface IdentidadGeneroRepository extends JpaRepository<IdentidadGenero, Integer>{

	/*
	 * public IdentidadGeneroRepository() { // TODO Auto-generated constructor stub
	 * }
	 */

}
