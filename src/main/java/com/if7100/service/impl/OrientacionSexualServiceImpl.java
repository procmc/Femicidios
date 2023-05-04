package com.if7100.service.impl;

import com.if7100.entity.OrientacionSexual;
import com.if7100.repository.OrientacionSexualRepository;
import com.if7100.service.OrientacionSexualService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrientacionSexualServiceImpl implements OrientacionSexualService {
	 
	
	private OrientacionSexualRepository orientacionRepository;
	
	public OrientacionSexualServiceImpl(OrientacionSexualRepository orientacionRepository) {
		super();
		this.orientacionRepository=orientacionRepository;
	}
	
	
	@Override
	public List<OrientacionSexual> getAllOrientacionesSexuales(){
		return orientacionRepository.findAll();
	}


	@Override
	public OrientacionSexual saveOrientacionSexual(OrientacionSexual orientacion) {
		// TODO Auto-generated method stub
		return orientacionRepository.save(orientacion);
	}


	@Override
	public OrientacionSexual getOrientacionSexualByCodigo(int codigo) {
		// TODO Auto-generated method stub
		return orientacionRepository.findById(codigo).get();
	}


	@Override
	public OrientacionSexual updateOrientacionSexual(OrientacionSexual orientacion) {
		// TODO Auto-generated method stub
		return orientacionRepository.save(orientacion);
	}


	@Override
	public void deleteOrientacionSexualByCodigo(int codigo) {
		// TODO Auto-generated method stub
		orientacionRepository.deleteById(codigo);
	}
	
	public OrientacionSexual getUsuarioByCV_Nombre(String CV_Titulo) {
		// TODO Auto-generated method stub
		return orientacionRepository.findByCVTitulo(CV_Titulo);
	}


	@Override
	public OrientacionSexual getOrientacionSexualByCVTitulo(String CVTitulo) {
		// TODO Auto-generated method stub
		return orientacionRepository.findByCVTitulo(CVTitulo);
	}

	@Override
	public OrientacionSexual getOrientacionSexualByCVDescripcion(String CVDescripcion){
		return orientacionRepository.findByCVDescripcion(CVDescripcion);
	}
}
