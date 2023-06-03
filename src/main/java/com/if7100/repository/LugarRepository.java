package com.if7100.repository;

import com.if7100.entity.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Integer> {

    List<Lugar> findByCIHecho(Integer CI_Hecho);
}
