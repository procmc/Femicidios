package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoRelacionFamiliar;

@Repository
public interface TipoRelacionFamiliarRepository extends JpaRepository<TipoRelacionFamiliar, Integer>{

    @Query("SELECT trf FROM TipoRelacionFamiliar trf JOIN TipoRelacionFamiliarPaises trfp ON trf.CI_Codigo = trfp.tipoRelacionFamiliar.CI_Codigo WHERE trfp.pais.Id = :codigoPais")
    List<TipoRelacionFamiliar> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais); 


}
