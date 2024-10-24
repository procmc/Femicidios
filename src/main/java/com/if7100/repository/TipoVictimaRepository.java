package com.if7100.repository;

import com.if7100.entity.TipoVictima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVictimaRepository extends JpaRepository<TipoVictima, Integer> {

    TipoVictima findByCVTitulo(String CVTitulo);

    TipoVictima findByCVDescripcion(String CVDescripcion);

}
