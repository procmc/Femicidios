package com.if7100.service;

import java.util.List;

import com.if7100.entity.Victima;



public interface VictimaService
{
		
	List<Victima> getAllVictima();
	
	Victima saveVictima(Victima victima);
	
	Victima getVictimaById(Integer Id);
	
	Victima updateVictima(Victima victima);
	
	void deleteVictimaById(Integer Id);

	Victima getVictimaByCVNombre(String CVNombre);
	
}
