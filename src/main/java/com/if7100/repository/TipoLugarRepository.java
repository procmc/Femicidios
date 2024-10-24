package com.if7100.repository;

import com.if7100.entity.TipoLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLugarRepository extends JpaRepository<TipoLugar, Integer> {

    TipoLugar findByCVTitulo(String CVTitulo);

    TipoLugar findByCVDescripcion(String CVDescripcion);

}
