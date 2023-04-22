package com.if7100.service;

import java.util.List;

import com.if7100.entity.Victima;



public interface VictimaService
{
		
	List<Victima> getAllVictima();
	
	Victima saveVictima(Victima usuario);
	
	Victima getVictimaById(Integer Id);
	
	Victima updateVictima(Victima usuario);
	
	void deleteVictimaById(Integer Id);
	
}
