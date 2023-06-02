package com.if7100.repository;

import com.if7100.entity.HechoOrganismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HechoOrganismoRepository extends JpaRepository<HechoOrganismo, Integer> {

    HechoOrganismo findByCIHecho(Integer CIHecho);

    HechoOrganismo findByCIOrganismo(Integer CIOrganismo);

}
