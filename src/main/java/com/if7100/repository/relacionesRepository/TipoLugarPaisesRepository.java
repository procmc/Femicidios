package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoLugar;
import com.if7100.entity.relacionesEntity.TipoLugarPaises;

@Repository
public interface TipoLugarPaisesRepository extends JpaRepository<TipoLugarPaises, Integer> {

    List<TipoLugarPaises> findByTipoLugar(
            @Param("tipoLugar") TipoLugar tipoLugar);

}
