package com.if7100.repository;

import com.if7100.entity.HechoImputado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HechoImputadoRepository extends JpaRepository<HechoImputado, Integer> {

//    HechoImputado findByCIHecho(Integer CIHecho);
//
//    HechoImputado findByCIImputado(Integer CIImputado);


    List<HechoImputado> findAllByCIHecho(Integer CIHecho);
    List<HechoImputado> findAllByCIImputado(Integer CIImputado);
}
