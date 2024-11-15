package com.if7100.repository;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.SituacionJuridica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SituacionJuridicaRepository extends JpaRepository<SituacionJuridica, Integer> {

    SituacionJuridica findByCVTitulo(String CVTitulo);

    SituacionJuridica findByCVDescripcion(String CVDescripcion);

    
    @Query("SELECT sj FROM SituacionJuridica sj JOIN SituacionJuridicaPaises sjp ON sj.CI_Codigo = sjp.situacionJuridica.CI_Codigo WHERE sjp.pais.Id = :codigoPais")
    List<SituacionJuridica> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);
}
