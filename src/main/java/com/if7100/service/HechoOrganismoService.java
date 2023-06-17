package com.if7100.service;

import com.if7100.entity.HechoOrganismo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HechoOrganismoService {

    List<HechoOrganismo> getAllHechoOrganismo();

    List<HechoOrganismo> getAllHechosOrganismo(Integer CI_Hecho);

    List<HechoOrganismo> getAllHechoOrganismos(Integer CI_Organismo);

    HechoOrganismo saveHechoOrganismo(HechoOrganismo hechoOrganismo);

    HechoOrganismo getHechoOrganismoById(Integer Id);

    HechoOrganismo updateHechoOrganismo(HechoOrganismo hechoOrganismo);

    void deleteHechoOrganismoById(Integer Id);

    List<HechoOrganismo> getHechoOrganismoByIdCIHecho(Integer CIHecho);

    List<HechoOrganismo> getHechoOrganismoBYIdCIOrganismo(Integer CIOrganismo);

    Page<HechoOrganismo> getAllHechoOrganismoPage(Pageable pageable);

    Page<HechoOrganismo> getAllHechosOrganismoPage(Pageable pageable, Integer CI_Hecho);

    Page<HechoOrganismo> getAllHechoOrganismosPage(Pageable pageable, Integer CI_Organismo);
}
