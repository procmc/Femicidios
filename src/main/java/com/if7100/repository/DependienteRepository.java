package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Dependiente;

@Repository
public interface DependienteRepository extends JpaRepository<Dependiente, Integer> {

	Dependiente findByCVDNI(String CVDNI);

	@Query("SELECT d FROM Dependiente d " +
       "JOIN FETCH d.tipoRelacionFamiliar trf " +
       "JOIN FETCH d.dependienteVictimas dv " +
       "JOIN FETCH dv.victima v")
List<Dependiente> findAllDependientesConRelacionesYVictimas();

}
