package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.SituacionJuridica;
import com.if7100.entity.relacionesEntity.SituacionJuridicaPaises;

@Repository
public interface SituacionJuridicaPaisesRepository extends JpaRepository<SituacionJuridicaPaises, Integer>{
    
     List<SituacionJuridicaPaises> findBySituacionJuridica(
            @Param("situacionJuridicaPaises") SituacionJuridica situacionJuridica);

}
