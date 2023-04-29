/**
 * 
 */
package com.if7100.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.Victima;

import com.if7100.repository.VictimaRepository;
import com.if7100.service.VictimaService;
/**
 * @author Hadji
 *
 */


@Service
public class VictimaServiceImpl implements VictimaService{
	
	private VictimaRepository victimaRepository;
	
	public VictimaServiceImpl ( VictimaRepository victimaRepository)
	{
		super();
		this.victimaRepository =victimaRepository;
	}
	
	@Override
	public List<Victima> getAllVictima(){
		return victimaRepository.findAll(); 
	}
	
	@Override
	public Victima saveVictima(Victima victima){
		return victimaRepository.save(victima); 
	}
	
		@Override
	public Victima getVictimaById(Integer Id){
		return victimaRepository.findById(Id).get(); 
	}
	
	@Override
	public Victima updateVictima (Victima victima){
		return victimaRepository.save(victima); 
	}
	
	@Override
	public void deleteVictimaById(Integer Id){
		victimaRepository.deleteById(Id); 
	}
	
	@Override
	public Victima getVictimaByCVNombre(String CVNombre){
		return victimaRepository.findByCVNombre(CVNombre); 
	}
	
	

}
