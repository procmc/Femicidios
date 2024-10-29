/**
 * 
 */
package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Imputado;
import com.if7100.entity.Lugar;
import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.TipoLugar;
import com.if7100.entity.Victima;
import com.if7100.repository.IdentidadGeneroRepository;
import com.if7100.repository.OrientacionSexualRepository;
import com.if7100.repository.VictimaRepository;
import com.if7100.service.VictimaService;
/**
 * @author Hadji
 *
 */


@Service
public class VictimaServiceImpl implements VictimaService{
	
	private VictimaRepository victimaRepository;
	private IdentidadGeneroRepository identidadGeneroRepository;
	private OrientacionSexualRepository orientacionSexualRepository;

	
	public VictimaServiceImpl ( VictimaRepository victimaRepository, IdentidadGeneroRepository identidadGeneroRepository, OrientacionSexualRepository orientacionSexualRepository)
	{
		super();
		this.victimaRepository =victimaRepository;
		this.identidadGeneroRepository =identidadGeneroRepository;
		this.orientacionSexualRepository= orientacionSexualRepository;
	}

	
	 
	@Override
	public List<Victima> getAllVictima(){
		return victimaRepository.findAll(); 
	}

	@Override
	public Page<Victima> getAllVictimaPage(Pageable pageable){
		return victimaRepository.findAll(pageable);
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

	@Override
	public List<IdentidadGenero> getAllIdentidadGeneros() {
	       List<Victima> victimas = victimaRepository.findAll();
	        List<IdentidadGenero> identidadGeneros = new ArrayList<>();
	        IdentidadGenero identidadGenero = new IdentidadGenero();
	        for (Victima victima :
	                victimas) {
	        	identidadGeneros.add(identidadGeneroRepository.findById(victima.getCVGenero()).orElse(identidadGenero));
	        }

	        return identidadGeneros;	
	}



	@Override
	public List<OrientacionSexual> getAllOrientacionSexuales() {
	       List<Victima> victimas = victimaRepository.findAll();
	        List<OrientacionSexual> orientacionSexuales = new ArrayList<>();
	        OrientacionSexual orientacionSexual = new OrientacionSexual();
	        for (Victima victima :
	                victimas) {
	        	orientacionSexuales.add(orientacionSexualRepository.findById(victima.getCVGenero()).orElse(orientacionSexual));
	        }

	        return orientacionSexuales;
	}
	
	

	//obtiene las victimas a partir del pais del hecho con el que se relaciona
	public List<Victima> findVictimasByCodigoPaisHecho(Integer codigoPais) {
        return victimaRepository.findByHechos_CodigoPais(codigoPais);
    }

	public List<Victima> findVictimasByCICodigoPais(Integer codigoPais) {
        return victimaRepository.findVictimasByCICodigoPais(codigoPais);
    }
}
