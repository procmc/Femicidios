package com.if7100.repository;
import com.if7100.entity.NivelEducativo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelEducativoRepository extends JpaRepository<NivelEducativo, Integer>{

	NivelEducativo findByCVTitulo(String CV_Titulo);

   NivelEducativo findByCVDescripcion(String CV_Descripcion);

    @Query("SELECT ne FROM NivelEducativo ne JOIN NivelEducativoPaises nep ON ne.CI_Id = nep.nivelEducativo.CI_Id WHERE nep.pais.Id = :codigoPais")
    List<NivelEducativo> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);
}
