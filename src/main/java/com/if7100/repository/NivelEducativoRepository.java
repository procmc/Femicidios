package com.if7100.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.NivelEducativo;

@Repository
public interface NivelEducativoRepository extends JpaRepository<NivelEducativo, Integer>{

	NivelEducativo findByCVTitulo(String CV_Titulo);

   NivelEducativo findByCVDescripcion(String CV_Descripcion);
}
