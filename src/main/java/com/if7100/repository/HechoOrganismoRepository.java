package com.if7100.repository;

import com.if7100.entity.HechoOrganismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HechoOrganismoRepository extends JpaRepository<HechoOrganismo, Integer> {

    List<HechoOrganismo> findAllByCIHecho(Integer CIHecho);

    List<HechoOrganismo> findAllByCIOrganismo(Integer CIOrganismo);

}
