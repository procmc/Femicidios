package com.if7100.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.if7100.entity.TipoVictima;
import com.if7100.repository.TipoVictimaRepository;
import com.if7100.service.TipoVictimaService;
/**
 * @author Liss
 * Fecha: 20 de abril del 2023
 */
@Service
public class TipoVictimaServiceImpl implements TipoVictimaService{
	
private TipoVictimaRepository TipoVictimaRepository;
	
	
    public TipoVictimaServiceImpl(TipoVictimaRepository TipoVictimaRepository) {
		super();
		this.TipoVictimaRepository=TipoVictimaRepository;
	}
    
    @Override
    public List<TipoVictima> getAllTipoVictima(){
    	return TipoVictimaRepository.findAll();
    }
     
    @Override
    public TipoVictima saveTipoVictima(TipoVictima victima){
    	return TipoVictimaRepository.save(victima);
    }
    
    @Override
    public void deleteTipoVictimaById(Integer Id){
    	TipoVictimaRepository.deleteById(Id);
    }
    
    @Override
    public TipoVictima getTipoVictimaById(Integer Id){
    	return TipoVictimaRepository.findById(Id).get();
    }
    
    @Override
    public TipoVictima updateTipoVictima(TipoVictima tipV){
    	return TipoVictimaRepository.save(tipV);
    }
    
    /*
    @Override
    public Usuario getUsuarioByCVNombre(String CV_Nombre){
    	return usuarioRepository.findByCVNombre(CV_Nombre);
    }
*/
}
