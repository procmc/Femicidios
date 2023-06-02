package com.if7100.service;

import java.util.List;

import com.if7100.entity.HechoOrganismo;

public interface HechoOrganismoService {

    List<HechoOrganismo> getAllHechoOrganismo();

    List<HechoOrganismo> getAllHechosOrganismo(Integer CI_Hecho);

    List<HechoOrganismo> getAllHechoOrganismos(Integer CI_Organismo);

    HechoOrganismo saveHechoOrganismo(HechoOrganismo hechoOrganismo);

    HechoOrganismo getHechoOrganismoById(Integer Id);

    HechoOrganismo updateHechoOrganismo(HechoOrganismo hechoOrganismo);

    void deleteHechoOrganismoById(Integer Id);

    HechoOrganismo getHechoOrganismoByIdHecho(Integer CIHecho);

    HechoOrganismo getHechoOrganismoBYIdOrganismo(Integer CIOrganismo);

}
