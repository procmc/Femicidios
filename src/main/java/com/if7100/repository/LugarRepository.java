package com.if7100.repository;

import com.if7100.entity.Lugar;
import com.if7100.entity.ProcesoJudicial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Integer> {

    List<Lugar> findByCIHecho(Integer CI_Hecho);

    
    @Query("SELECT l FROM Lugar l JOIN Hecho h ON l.CIHecho = h.id WHERE h.codigoPais = :codigoPais")
	List<Lugar> findLugarByCodigoPais(@Param("codigoPais") Integer codigoPais);
}
