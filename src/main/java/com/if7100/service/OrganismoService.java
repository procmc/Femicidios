/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Organismo;

/**
 * @author Adam Smasher
 *
 */
public interface OrganismoService {
 List<Organismo> getAllOrganismos();

 Organismo saveOrganismo(Organismo organismo);
 
 Organismo getOrganismoById(int id);

 Organismo updateOrganismo(Organismo organismo);
 
 void deleteOrganismoById(int id);
 
 Organismo getOrganismoByCVNombre(String CV_Nombre);
 
}
