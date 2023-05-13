package com.if7100.repository;
import com.if7100.entity.NivelEducativo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelEducativoRepository extends JpaRepository<NivelEducativo, Integer>{

	NivelEducativo findByCV_Titulo(String CV_Titulo);

   NivelEducativo findByCV_Descripcion(String CV_Descripcion);
}
