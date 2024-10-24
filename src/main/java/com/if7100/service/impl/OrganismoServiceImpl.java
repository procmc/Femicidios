package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.if7100.entity.Organismo;
import com.if7100.entity.Victima;
import com.if7100.repository.OrganismoRepository;
import com.if7100.service.OrganismoService;

@Service
public class OrganismoServiceImpl implements OrganismoService {
	 
	
	private OrganismoRepository organismoRepository;
	
	public OrganismoServiceImpl(OrganismoRepository organismoRepository) {
		super();
		this.organismoRepository=organismoRepository;
	}
	
	
	@Override
	public List<Organismo> getAllOrganismos(){
		return organismoRepository.findAll();
	}

	@Override
	public Page<Organismo> getAllOrganismosPage(Pageable pageable){
		return organismoRepository.findAll(pageable);
	}

	@Override
	public Organismo saveOrganismo(Organismo organismo) {
		// TODO Auto-generated method stub
		return organismoRepository.save(organismo);
	}


	@Override
	public Organismo getOrganismoById(int id) {
		// TODO Auto-generated method stub
		return organismoRepository.findById(id).get();
	}


	@Override
	public Organismo updateOrganismo(Organismo organismo) {
		// TODO Auto-generated method stub
		return organismoRepository.save(organismo);
	}


	@Override
	public void deleteOrganismoById(int id) {
		// TODO Auto-generated method stub
		organismoRepository.deleteById(id);
	}
	
	public Organismo getOrganismoByCV_Nombre(String CV_Nombre) {
		// TODO Auto-generated method stub
		return organismoRepository.findByCVNombre(CV_Nombre);
	}


	@Override
	public Organismo getOrganismoByCVNombre(String CV_Nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	    //obtiene los usuarios por codigo país
    @Override
    public List<Organismo> findByCodigoPais(Integer codigoPais) {
        return organismoRepository.findByCodigoPais(codigoPais);
    }
}
