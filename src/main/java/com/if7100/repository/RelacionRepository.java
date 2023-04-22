package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Relacion;


@Repository
public interface RelacionRepository extends JpaRepository <Relacion, Integer>{

	Relacion findByCVtitulo(String CVtitulo);

}
