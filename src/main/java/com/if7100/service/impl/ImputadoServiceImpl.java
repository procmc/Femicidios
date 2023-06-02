package com.if7100.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.if7100.entity.Imputado;
import com.if7100.repository.ImputadoRepository;
import com.if7100.service.ImputadoService;

@Service
public class ImputadoServiceImpl implements ImputadoService {
	 
	
	private ImputadoRepository imputadoRepository;
	
	public ImputadoServiceImpl(ImputadoRepository imputadoRepository) {
		super();
		this.imputadoRepository=imputadoRepository;
	}
	
	
	@Override
	public List<Imputado> getAllUsuarios(){
		return imputadoRepository.findAll();
	}


	@Override
	public Imputado saveImputado(Imputado imputado) {
		// TODO Auto-generated method stub
		return imputadoRepository.save(imputado);
	}


	@Override
	public Imputado getImputadoById(int id) {
		// TODO Auto-generated method stub
		return imputadoRepository.findById(id).get();
	}


	@Override
	public Imputado updateImputado(Imputado imputado) {
		// TODO Auto-generated method stub
		return imputadoRepository.save(imputado);
	}


	@Override
	public void deleteImputadoById(int id) {
		// TODO Auto-generated method stub
		imputadoRepository.deleteById(id);
	}
	
	public Imputado getImputadoByCV_Nombre(String CV_Nombre) {
		// TODO Auto-generated method stub
		return imputadoRepository.findByCVNombre(CV_Nombre);
	}


	@Override
	public Imputado getImputadoByCVNombre(String CV_Nombre) {
		// TODO Auto-generated method stub
		return null;
	}
}
