package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoVictima;
import com.if7100.entity.relacionesEntity.TipoVictimaPaises;

@Repository
public interface TipoVictimaPaisesRepository extends JpaRepository<TipoVictimaPaises, Integer>{
    
     List<TipoVictimaPaises> findByTipoVictima(
            @Param("tipoVictima") TipoVictima tipoVictima);

}
