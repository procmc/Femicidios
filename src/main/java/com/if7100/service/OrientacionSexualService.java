/**
 * 
 */
package com.if7100.service;

import com.if7100.entity.OrientacionSexual;

import java.util.List;

/**
 * @author Ronny Salgado
 *
 */
public interface OrientacionSexualService {
 List<OrientacionSexual> getAllOrientacionesSexuales();

 OrientacionSexual saveOrientacionSexual(OrientacionSexual orientacionSexual);
 
 OrientacionSexual getOrientacionSexualByCodigo(int codigo);

 OrientacionSexual updateOrientacionSexual(OrientacionSexual orientacion);
 
 void deleteOrientacionSexualByCodigo(int codigo);
 
 OrientacionSexual getOrientacionSexualByCVTitulo(String CVTitulo);

 OrientacionSexual getOrientacionSexualByCVDescripcion(String CVDescripcion);
 
}
