/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.OrientacionSexual;

/**
 * @author Ronny Salgado
 *
 */
public interface OrientacionSexualService {
 List<OrientacionSexual> getAllOrientacionesSexuales();

 OrientacionSexual saveOrientacionSexual(OrientacionSexual usuario);
 
 OrientacionSexual getOrientacionSexualByCodigo(int codigo);

 OrientacionSexual updateOrientacionSexual(OrientacionSexual orientacion);
 
 void deleteOrientacionSexualByCodigo(int codigo);
 
 OrientacionSexual getOrientacionSexualByCVTitulo(String CV_Titulo);
 
}
