package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.relacionesEntity.NivelEducativoPaises;

@Repository
public interface NivelEducativoPaisesRepository extends JpaRepository<NivelEducativoPaises, Integer> {

    List<NivelEducativoPaises> findByNivelEducativo(
            @Param("nivelEducativo") NivelEducativo nivelEducativo);

}
