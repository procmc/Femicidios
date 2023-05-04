package com.if7100.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.Lugar;
import com.if7100.entity.TipoLugar;
import com.if7100.repository.LugarRepository;
import com.if7100.repository.TipoLugarRepository;
import com.if7100.service.LugarService;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
@Service
public class LugarServiceImpl implements LugarService{

    @Autowired
    private LugarRepository lugarRepository;
    private TipoLugarRepository tipoLugarRepository;

    public LugarServiceImpl(LugarRepository lugarRepository, TipoLugarRepository tipoLugarRepository) {
        super();
        this.lugarRepository=lugarRepository;
        this.tipoLugarRepository=tipoLugarRepository;
    }









    /*


    public List<Lugar> getAllLugares(String palabraClave){
    	List<Lugar> salida;
    	if(palabraClave!=null) {
    		salida= lugarRepository.findAll(palabraClave);
    	}
    	else {
        salida = lugarRepository.findAll();
    	}
        for (Lugar lugar : salida) {
            TipoLugar tipoLugar = tipoLugarRepository.findById(lugar.getCI_Tipo_Lugar()).orElse(null);
            if (tipoLugar != null) {
                lugar.setTitulo(tipoLugar.getCVTitulo());
            }
        }
        return salida;
    }
    */



    public List<Lugar> getAllLugar(){
        List<Lugar> salida= lugarRepository.findAll();

        for (Lugar lugar : salida) {
            TipoLugar tipoLugar = tipoLugarRepository.findById(lugar.getCI_Tipo_Lugar()).orElse(null);
            if (tipoLugar != null) {
                lugar.setTitulo(tipoLugar.getCVTitulo());
            }
        }
        return salida;
    }







    public List<Lugar> getAllLugares(Integer CI_Hecho){
        List<Lugar> salida= lugarRepository.findByCIHecho(CI_Hecho);

        for (Lugar lugar : salida) {
            TipoLugar tipoLugar = tipoLugarRepository.findById(lugar.getCI_Tipo_Lugar()).orElse(null);
            if (tipoLugar != null) {
                lugar.setTitulo(tipoLugar.getCVTitulo());
            }
        }
        return salida;
    }



   /* @Override
    public List<Lugar> getAllLugares(){
    	return lugarRepository.findAll();

    }*/



    @Override
    public Lugar saveLugar(Lugar lugar){
        return lugarRepository.save(lugar);
    }

    @Override
    public Lugar getLugarById(Integer Id){
        return lugarRepository.findById(Id).get();
    }

    @Override
    public void deleteLugarById(Integer Id){
        lugarRepository.deleteById(Id);
    }

    @Override
    public Lugar updateLugar(Lugar lugar){
        return lugarRepository.save(lugar);
    }


   /* @Override
    public Usuario getUsuarioByCVNombre(String CV_Nombre){
    	return usuarioRepository.findByCVNombre(CV_Nombre);
    }*/



}
