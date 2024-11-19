/**
 * 
 */

package com.if7100.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import com.if7100.service.TipoOrganismoService;
import com.if7100.entity.TipoOrganismo;
import com.if7100.repository.TipoOrganismoRepository;

/**
 * @author Adam Smasher
 */

@Service
public class TipoOrganismoServiceImpl implements TipoOrganismoService{

	private TipoOrganismoRepository tipoOrganismoRepository;
	
	public TipoOrganismoServiceImpl (TipoOrganismoRepository tipoOrganismoRepository) {
		super();
		this.tipoOrganismoRepository = tipoOrganismoRepository;
	}

	@Override
	public List<TipoOrganismo> getAllTipoOrganismos() {
		return tipoOrganismoRepository.findAll();
	}

	@Override
	public Page<TipoOrganismo> getAllTipoOrganismosPage(Pageable pageable){
		return tipoOrganismoRepository.findAll(pageable);
	}

	@Override
	public TipoOrganismo saveTipoOrganismo(TipoOrganismo tipoOrganismo) {
		return tipoOrganismoRepository.save(tipoOrganismo);
	}

	@Override
	public TipoOrganismo getTipoOrganismoByCodigo(Integer Codigo) {
		return tipoOrganismoRepository.findById(Codigo).get();
	}

	@Override
	public TipoOrganismo updateTipoOrganismo(TipoOrganismo tipoOrganismo) {
		return tipoOrganismoRepository.save(tipoOrganismo);
	}

	@Override
	public void deleteTipoOrganismoByCodigo(Integer Codigo) {
		tipoOrganismoRepository.deleteById(Codigo);
		
	}

	@Override
	public TipoOrganismo getTipoOrganismoByCVTitulo(String CVTitulo) {
		return tipoOrganismoRepository.findByCVTitulo(CVTitulo);
	}
 	
	     //////
	public List<TipoOrganismo> getTipoOrganismoByCodigoPais(Integer codigoPais) {
        return tipoOrganismoRepository.findAllByCodigoPais(codigoPais);
    }
	
}