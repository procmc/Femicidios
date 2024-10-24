package com.if7100.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.if7100.entity.Dependiente;
import com.if7100.entity.DependienteVictima;


public interface DependienteVictimaRepository extends JpaRepository<DependienteVictima, Integer> {

     // Método personalizado para eliminar las relaciones por el ID del dependiente
    @Modifying
    @Query("DELETE FROM DependienteVictima dv WHERE dv.dependiente.CI_Codigo = :CI_Codigo_Dependiente")
    void deleteByDependienteId(@Param("CI_Codigo_Dependiente") Integer CI_Codigo_Dependiente);

    // Este método buscará todas las relaciones DependienteVictima asociadas con un dependiente
    List<DependienteVictima> findBydependiente(Dependiente dependiente);
    
}
