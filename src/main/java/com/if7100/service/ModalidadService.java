
package com.if7100.service;

import java.util.List;
import com.if7100.entity.Modalidad;
/**
 * @author tishary foster
 * Fecha 19 de abril 2023
 */
public interface ModalidadService {
	
	List <Modalidad> getAllModalidad();
	Modalidad saveModalidad (Modalidad modalidad);
	Modalidad getModalidadById(Integer codigo);
	Modalidad updateModalidad(Modalidad modalidad);
	void deleteModalidadById(Integer codigo);
	Modalidad getModalidadByCVTitulo(String CVTitulo);
	

}
