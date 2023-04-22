package com.if7100.service;

import java.util.List;

import com.if7100.entity.Relacion;


public interface RelacionService {

	List<Relacion> getAllTypeRelaciones();
	
	Relacion saveRelacion (Relacion typerelacion);
	
	Relacion getRelacionById(Integer Id);
	
	Relacion updateRelacion(Relacion relacion);
	
	void deleteRelacionById(Integer Id);
	
	Relacion getRelacionByCVNombr(String CVtitulo);
	
}
