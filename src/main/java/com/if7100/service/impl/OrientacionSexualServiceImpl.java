package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.OrientacionSexual;
import com.if7100.repository.OrientacionSexualRepository;
import com.if7100.service.OrientacionSexualService;

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
	public Page<OrientacionSexual> getAllOrientacionesSexualesPage(Pageable pageable){
		return orientacionRepository.findAll(pageable);
	}

	@Override
	public OrientacionSexual saveOrientacionSexual(OrientacionSexual orientacion) {
		// TODO Auto-generated method stub
		return orientacionRepository.save(orientacion);
	}


	@Override
	public OrientacionSexual getOrientacionSexualByCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return orientacionRepository.findById(codigo).get();
	}


	@Override
	public OrientacionSexual updateOrientacionSexual(OrientacionSexual orientacion) {
		// TODO Auto-generated method stub
		return orientacionRepository.save(orientacion);
	}


	@Override
	public void deleteOrientacionSexualByCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		orientacionRepository.deleteById(codigo);
	}
	
	public OrientacionSexual getUsuarioByCV_Nombre(String CV_Titulo) {
		// TODO Auto-generated method stub
		return orientacionRepository.findByCVTitulo(CV_Titulo);
	}


	@Override
	public OrientacionSexual getOrientacionSexualByCVTitulo(String CV_Titulo) {
		// TODO Auto-generated method stub
		return null;
	}


	//////
	public List<OrientacionSexual> getOrientacionSexualByCodigoPais(Integer codigoPais) {
        return orientacionRepository.findAllByCodigoPais(codigoPais);
    }

}
