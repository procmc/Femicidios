/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.OrientacionSexual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Ronny Salgado
 *
 */
public interface OrientacionSexualService {
 List<OrientacionSexual> getAllOrientacionesSexuales();

 OrientacionSexual saveOrientacionSexual(OrientacionSexual usuario);
 
 OrientacionSexual getOrientacionSexualByCodigo(Integer codigo);

 OrientacionSexual updateOrientacionSexual(OrientacionSexual orientacion);
 
 void deleteOrientacionSexualByCodigo(Integer codigo);
 
 OrientacionSexual getOrientacionSexualByCVTitulo(String CV_Titulo);

    Page<OrientacionSexual> getAllOrientacionesSexualesPage(Pageable pageable);
}
