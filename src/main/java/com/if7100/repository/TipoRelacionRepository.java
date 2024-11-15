package com.if7100.repository;

import com.if7100.entity.TipoRelacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRelacionRepository extends JpaRepository<TipoRelacion, Integer> {

    TipoRelacion findByCVTitulo(String CVTitulo);

    TipoRelacion findByCVDescripcion(String CVDescripcion);

    @Query("SELECT tr FROM TipoRelacion tr JOIN TipoRelacionPaises trp ON tr.CI_Codigo = trp.tipoRelacion.CI_Codigo WHERE trp.pais.Id = :codigoPais")
    List<TipoRelacion> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);


}
