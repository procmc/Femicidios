package com.if7100.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.repository.ProcesoJudicialRepository;
import com.if7100.service.ProcesoJudicialService;

@Service
public class ProcesoJudicialServiceImpl implements ProcesoJudicialService {
	 
	
	private ProcesoJudicialRepository procesoJudicialRepository;
	
	public ProcesoJudicialServiceImpl(ProcesoJudicialRepository procesoJudicialRepository) {
		super();
		this.procesoJudicialRepository=procesoJudicialRepository;
	}
	
	
	@Override
	public List<ProcesoJudicial> getAllProcesosJudiciales(){
		return procesoJudicialRepository.findAll();
	}


	@Override
	public ProcesoJudicial saveProcesoJudicial(ProcesoJudicial procesoJudicial) {
		// TODO Auto-generated method stub
		return procesoJudicialRepository.save(procesoJudicial);
	}


	@Override
	public ProcesoJudicial getProcesoJudicialById(int id) {
		// TODO Auto-generated method stub
		return procesoJudicialRepository.findById(id).get();
	}


	@Override
	public ProcesoJudicial updateProcesoJudicial(ProcesoJudicial procesoJudicial) {
		// TODO Auto-generated method stub
		return procesoJudicialRepository.save(procesoJudicial);
	}


	@Override
	public void deleteProcesoJudicialById(int id) {
		// TODO Auto-generated method stub
		procesoJudicialRepository.deleteById(id);
	}
	
	public ProcesoJudicial getProcesoJudicialByCI_Denunciante(int CI_Denunciante) {
		// TODO Auto-generated method stub
		return procesoJudicialRepository.findByCIDenunciante(CI_Denunciante);
	}


	@Override
	public ProcesoJudicial getProcesoJudicialByCIDenunciante(int CI_Denunciante) {
		// TODO Auto-generated method stub
		return null;
	}
}
