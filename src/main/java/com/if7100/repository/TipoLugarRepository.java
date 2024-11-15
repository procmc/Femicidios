package com.if7100.repository;

import com.if7100.entity.TipoLugar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLugarRepository extends JpaRepository<TipoLugar, Integer> {

    TipoLugar findByCVTitulo(String CVTitulo);

    TipoLugar findByCVDescripcion(String CVDescripcion);

    @Query("SELECT tl FROM TipoLugar tl JOIN TipoLugarPaises tlp ON tl.CI_Codigo = tlp.tipoLugar.CI_Codigo WHERE tlp.pais.Id = :codigoPais")
    List<TipoLugar> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);

}
