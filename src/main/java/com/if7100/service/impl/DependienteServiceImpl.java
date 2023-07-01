/**
 * 
 */
package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Dependiente;

import com.if7100.entity.TipoRelacion;
import com.if7100.repository.DependienteRepository;
import com.if7100.repository.TipoRelacionRepository;
import com.if7100.service.DependienteService;

/**
 * @author Hadji
 *
 */


@Service
public class DependienteServiceImpl implements DependienteService{
	
	private DependienteRepository dependienteRepository;
	
	  private TipoRelacionRepository tipoRelacionRepository;
	
	public DependienteServiceImpl ( DependienteRepository dependienteRepository, TipoRelacionRepository tipoRelacionRepository)
	{
		super();
		this.dependienteRepository =dependienteRepository;
	    this.tipoRelacionRepository = tipoRelacionRepository;
	}
	
	@Override
	public List<Dependiente> getAllDependiente(){
		return dependienteRepository.findAll(); 
	}

	@Override
	public Page<Dependiente> getAllDependientePage(Pageable pageable){
		return dependienteRepository.findAll(pageable);
	}
	
	//
	
	@Override
    public List<TipoRelacion> getAllTipoRelaciones() {
        List<Dependiente> dependientes = dependienteRepository.findAll();
        List<TipoRelacion> tipoRelaciones = new ArrayList<>();
        TipoRelacion tipoRelacion = new TipoRelacion();
        for (Dependiente dependiente :
        	dependientes) {
            tipoRelaciones.add(tipoRelacionRepository.findById(dependiente.getCI_Tiporelacion()).orElse(tipoRelacion));
        }

        return tipoRelaciones;
    }

    @Override
    public List<TipoRelacion> getAllTipoRelacionesPage(Pageable pageable){
        Page<Dependiente> dependientes = dependienteRepository.findAll(pageable);
        List<TipoRelacion> tipoRelaciones = new ArrayList<>();
        TipoRelacion tipoRelacion = new TipoRelacion();
        for (Dependiente dependiente :
        	dependientes) {
            tipoRelaciones.add(tipoRelacionRepository.findById(dependiente.getCI_Tiporelacion()).orElse(tipoRelacion));
        }
        return tipoRelaciones;
    }
	
	
	
	//
	

	@Override
	public Dependiente saveDependiente(Dependiente dependiente){
		return dependienteRepository.save(dependiente); 
	}
	
		@Override
	public Dependiente getDependienteById(Integer Id){
		return dependienteRepository.findById(Id).get(); 
	}
	
	@Override
	public Dependiente updateDependiente (Dependiente dependiente){
		return dependienteRepository.save(dependiente); 
	}
	
	@Override
	public void deleteDependienteById(Integer Id){
		dependienteRepository.deleteById(Id); 
	}
	
	@Override
	public Dependiente getDependienteByCVDNI(String CVNombre){
		return dependienteRepository.findByCVDNI(CVNombre); 
	}
	
	

}
