package com.if7100.repository;

import com.if7100.entity.SituacionJuridica;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SituacionJuridicaRepository extends JpaRepository<SituacionJuridica, Integer> {

    SituacionJuridica findByCVTitulo(String CVTitulo);

    SituacionJuridica findByCVDescripcion(String CVDescripcion);

}
