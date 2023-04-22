package com.if7100.service;

import java.util.List;

import com.if7100.entity.TipoVictima;
/**
 * @author Liss
 * Fecha: 20 de abril del 2023
 */
public interface TipoVictimaService {
	
	List<TipoVictima> getAllTipoVictima();

	TipoVictima saveTipoVictima(TipoVictima victima);
	
	void deleteTipoVictimaById(Integer Id);
	
	
	TipoVictima getTipoVictimaById(Integer Id);

	
	TipoVictima updateTipoVictima(TipoVictima victima);

	/*

	TipoVictima getTipoVictimaByCVNombre(String CV_Titulo);*/

}
