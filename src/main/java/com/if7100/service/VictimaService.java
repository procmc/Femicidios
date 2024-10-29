package com.if7100.service;

import java.util.List;

import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Imputado;
import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.Victima;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface VictimaService
{
		
	List<Victima> getAllVictima();
	List<IdentidadGenero> getAllIdentidadGeneros();
	List<OrientacionSexual> getAllOrientacionSexuales();

	
	Victima saveVictima(Victima victima);
	
	Victima getVictimaById(Integer Id);
	
	Victima updateVictima(Victima victima);
	
	void deleteVictimaById(Integer Id);

	Victima getVictimaByCVNombre(String CVNombre);

    Page<Victima> getAllVictimaPage(Pageable pageable);

	//obtener las victimas a partir del pais del hecho al que se relaciona
	List<Victima> findVictimasByCodigoPaisHecho(Integer codigoPais);

	List<Victima> findVictimasByCICodigoPais(Integer codigoPais);

}
