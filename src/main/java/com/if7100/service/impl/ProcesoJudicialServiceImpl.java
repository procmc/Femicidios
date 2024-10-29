package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Hecho;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.repository.ProcesoJudicialRepository;
import com.if7100.service.ProcesoJudicialService;

@Service
public class ProcesoJudicialServiceImpl implements ProcesoJudicialService {
	 
	
	private ProcesoJudicialRepository procesoJudicialRepository;
	

	@Override
    public ProcesoJudicial findByCVEstado(String CVEstado) {
        return procesoJudicialRepository.findByCVEstado(CVEstado);
    }

	public ProcesoJudicialServiceImpl(ProcesoJudicialRepository procesoJudicialRepository) {
		super();
		this.procesoJudicialRepository=procesoJudicialRepository;
	}
	
	
	@Override
	public List<ProcesoJudicial> getAllProcesosJudiciales(){
		return procesoJudicialRepository.findAll();
	}

	@Override
	public Page<ProcesoJudicial> getAllProcesosJudicialesPage(Pageable pageable){
		return procesoJudicialRepository.findAll(pageable);
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
	



	/*@Override
    public List<ProcesoJudicial> getProcesosJudicialesByCodigoPaisUsuario(Integer codigoPaisUsuario) {
        return procesoJudicialRepository.findProcesosByCodigoPais(codigoPaisUsuario);
    }*/

	@Override
	public List<ProcesoJudicial> findProcesoJudicialByCICodigoPais(Integer CICodigoPais) {
		return procesoJudicialRepository.findProcesoJudicialByCICodigoPais(CICodigoPais);
	}
}
