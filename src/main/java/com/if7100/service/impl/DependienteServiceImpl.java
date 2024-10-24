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
import com.if7100.entity.DependienteVictima;
import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.repository.DependienteRepository;
import com.if7100.repository.DependienteVictimaRepository;
import com.if7100.repository.TipoRelacionFamiliarRepository;
import com.if7100.repository.TipoRelacionRepository;
import com.if7100.service.DependienteService;

import jakarta.transaction.Transactional;

/**
 * @author Hadji
 *
 */


@Service
public class DependienteServiceImpl implements DependienteService{
	
	private DependienteRepository dependienteRepository;
	
	private TipoRelacionFamiliarRepository tipoRelacionFamiliarRepository;
	private final DependienteVictimaRepository dependienteVictimaRepository;
	
	public DependienteServiceImpl ( DependienteRepository dependienteRepository, TipoRelacionFamiliarRepository tipoRelacionFamiliarRepository,
	DependienteVictimaRepository dependienteVictimaRepository)
	{
		super();
		this.dependienteRepository =dependienteRepository;
	    this.tipoRelacionFamiliarRepository = tipoRelacionFamiliarRepository;
		this.dependienteVictimaRepository = dependienteVictimaRepository;
	}

	@Override
    public List<Dependiente> findAllDependientesConRelacionesYVictimas() {
        return dependienteRepository.findAllDependientesConRelacionesYVictimas();
    }

	
	@Override
    public List<DependienteVictima> findBydependiente(Dependiente dependiente) {
        return dependienteVictimaRepository.findBydependiente(dependiente);
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
    public List<TipoRelacionFamiliar> getAllTipoRelacionesFamilires() {
        List<Dependiente> dependientes = dependienteRepository.findAll();
        List<TipoRelacionFamiliar> tipoRelaciones = new ArrayList<>();
        TipoRelacionFamiliar tipoRelacionFamiliar = new TipoRelacionFamiliar();
        for (Dependiente dependiente :
        	dependientes) {
            tipoRelaciones.add(tipoRelacionFamiliarRepository.findById(dependiente.getTipoRelacionFamiliar().getCI_Codigo()).orElse(tipoRelacionFamiliar));
        }

        return tipoRelaciones;
    }

    @Override
    public List<TipoRelacionFamiliar> getAllTipoRelacionesFamiliaresPage(Pageable pageable){
        Page<Dependiente> dependientes = dependienteRepository.findAll(pageable);
        List<TipoRelacionFamiliar> tipoRelaciones = new ArrayList<>();
        TipoRelacionFamiliar tipoRelacionFamiliar = new TipoRelacionFamiliar();
        for (Dependiente dependiente :
        	dependientes) {
            tipoRelaciones.add(tipoRelacionFamiliarRepository.findById(dependiente.getTipoRelacionFamiliar().getCI_Codigo()).orElse(tipoRelacionFamiliar));
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
	
	/*@Override
	public Dependiente updateDependiente (Dependiente dependiente){
		return dependienteRepository.save(dependiente); 
	}*/
	
	@Override
	public void deleteDependienteById(Integer Id){
		
		dependienteRepository.deleteById(Id); 
	}
	
	@Override
	public Dependiente getDependienteByCVDNI(String CVNombre){
		return dependienteRepository.findByCVDNI(CVNombre); 
	}
	
	

	@Override
    public void saveDependienteVictima(DependienteVictima dependienteVictima) {
        dependienteVictimaRepository.save(dependienteVictima);
    }

	@Override
    public void updateDependienteVictima(DependienteVictima dependienteVictima) {
        dependienteVictimaRepository.save(dependienteVictima);
    }

    @Override
    public void updateDependiente(Dependiente dependiente) {
        // Guardar el dependiente actualizado
        dependienteRepository.save(dependiente);

        // Obtener y actualizar las relaciones DependienteVictima
        List<DependienteVictima> dependienteVictimaList = dependienteVictimaRepository.findBydependiente(dependiente);
        
        for (DependienteVictima dependienteVictima : dependienteVictimaList) {
            dependienteVictima.setDependiente(dependiente); // Actualizar la relación dependiente
            dependienteVictimaRepository.save(dependienteVictima);
        }
    }
}
