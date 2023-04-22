package com.if7100.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import com.if7100.service.VictimaService;
import com.if7100.repository.UsuarioRepository;
import com.if7100.repository.VictimaRepository;
import com.if7100.entity.Usuario;
import com.if7100.entity.Victima;

public class VictimaServiceImp implements VictimaService {
	
	private VictimaRepository victimaRepository;

	public VictimaServiceImp(VictimaRepository victimaRepository) 
	{   super();
		this.victimaRepository = victimaRepository;
	}
	

	
	
	@Override
	public Victima saveVictima(Victima victima) {
		return victimaRepository.save(victima);
		
	}
	@Override
	public Victima getVictimaById(Integer Id) {
		
		return victimaRepository.findById(Id).get();
	}
	
	@Override
	public Victima updateVictima(Victima usuario) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void deleteVictimaById(Integer Id) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	public VictimaRepository getUsuarioRepository() {
		return victimaRepository;
	}

	public void setVictimaRepository(UsuarioRepository usuarioRepository) {
		this.victimaRepository = victimaRepository;
	}

	@Override
	public List<Victima> getAllVictima() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
