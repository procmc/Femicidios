package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Dependiente;


@Repository
public interface DependienteRepository extends JpaRepository<Dependiente, Integer> { 

	Dependiente findByCVDNI(String CVDNI);
}
