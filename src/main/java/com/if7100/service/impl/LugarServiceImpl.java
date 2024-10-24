package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Hecho;
import com.if7100.entity.Lugar;
import com.if7100.entity.ProcesoJudicial;
import com.if7100.entity.TipoLugar;
import com.if7100.entity.TipoRelacion;
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

    @Override
    public List<Lugar> getLugarByCodigoPaisUsuario(Integer codigoPaisUsuario) {
        return lugarRepository.findLugarByCodigoPais(codigoPaisUsuario);
    }

    public List<Lugar> getAllLugar(){
        return lugarRepository.findAll();
//        List<Lugar> salida= lugarRepository.findAll();
//
//        for (Lugar lugar : salida) {
//            TipoLugar tipoLugar = tipoLugarRepository.findById(lugar.getCI_Tipo_Lugar()).orElse(null);
//            if (tipoLugar != null) {
//                lugar.setTitulo(tipoLugar.getCVTitulo());
//            }
//        }
//        return salida;
    }

    public Page<Lugar> getAllLugarPage(Pageable pageable){
        return lugarRepository.findAll(pageable);
    }

    public List<Lugar> getAllLugares(Integer CI_Hecho){

        List<Lugar> salida = lugarRepository.findAll();

        return salida.stream().filter(lugar -> Objects.equals(lugar.getCIHecho(), CI_Hecho)).collect(Collectors.toList());
//        List<Lugar> salida= lugarRepository.findByCIHecho(CI_Hecho);
//
//        for (Lugar lugar : salida) {
//            TipoLugar tipoLugar = tipoLugarRepository.findById(lugar.getCI_Tipo_Lugar()).orElse(null);
//            if (tipoLugar != null) {
//                lugar.setTitulo(tipoLugar.getCVTitulo());
//            }
//        }
//        return salida;
    }

    public Page<Lugar> getAllLugaresPage(Pageable pageable, Integer CI_Hecho){

        List<Lugar> salida = lugarRepository.findAll();
        salida = salida.stream().filter(lugar -> Objects.equals(lugar.getCIHecho(), CI_Hecho)).collect(Collectors.toList());
        return new PageImpl<>(salida, pageable, salida.size());

    }

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

	@Override
	public List<TipoLugar> getAllTipoLugars() {
        List<Lugar> lugares = lugarRepository.findAll();
        List<TipoLugar> tipolugares = new ArrayList<>();
        TipoLugar tipoLugar = new TipoLugar();
        for (Lugar lugar :
                lugares) {
        	tipolugares.add(tipoLugarRepository.findById(lugar.getCITipoLugar()).orElse(tipoLugar));
        }

        return tipolugares;	
        
	}




}
