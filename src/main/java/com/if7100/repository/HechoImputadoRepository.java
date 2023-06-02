package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.HechoImputado;

@Repository
public interface HechoImputadoRepository extends JpaRepository<HechoImputado, Integer> {


    HechoImputado findByCIHecho(Integer CIHecho);

    HechoImputado findByCIImputado(Integer CIImputado);

}
