/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Organismo;
import com.if7100.entity.Victima;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

 Page<Organismo> getAllOrganismosPage(Pageable pageable);

 public List<Organismo> findByCodigoPais(Integer codigoPais);//obtiene los usuarios por codigo país

}
