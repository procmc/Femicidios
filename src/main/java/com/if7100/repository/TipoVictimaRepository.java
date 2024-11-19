package com.if7100.repository;

import com.if7100.entity.TipoVictima;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVictimaRepository extends JpaRepository<TipoVictima, Integer> {

    TipoVictima findByCVTitulo(String CVTitulo);

    TipoVictima findByCVDescripcion(String CVDescripcion);

    @Query("SELECT tv FROM TipoVictima tv JOIN TipoVictimaPaises tvp ON tv.CI_Codigo = tvp.tipoVictima.CI_Codigo WHERE tvp.pais.Id = :codigoPais")
    List<TipoVictima> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);


}
