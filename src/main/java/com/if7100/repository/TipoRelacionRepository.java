package com.if7100.repository;

import com.if7100.entity.TipoRelacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRelacionRepository extends JpaRepository<TipoRelacion, Integer> {

    TipoRelacion findByCVTitulo(String CVTitulo);

    TipoRelacion findByCVDescripcion(String CVDescripcion);


}
