package com.if7100.service.impl;

import com.if7100.entity.Hecho;
import com.if7100.entity.HechoImputado;
import com.if7100.entity.Lugar;
import com.if7100.entity.TipoLugar;
import com.if7100.repository.HechoImputadoRepository;
import com.if7100.repository.HechoRepository;
import com.if7100.repository.ImputadoRepository;
import com.if7100.repository.HechoImputadoRepository;
import com.if7100.service.HechoImputadoService;
import com.if7100.service.HechoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HechoImputadoServiceImpl implements HechoImputadoService {

    private HechoImputadoRepository hechoImputadoRepository;

    private HechoRepository hechoRepository;

    private ImputadoRepository imputadoRepository;

    public HechoImputadoServiceImpl(HechoImputadoRepository hechoImputadoRepository, HechoRepository hechoRepository, ImputadoRepository imputadoRepository){
        super();
        this.hechoImputadoRepository = hechoImputadoRepository;
        this.hechoRepository = hechoRepository;
        this.imputadoRepository = imputadoRepository;
    }

    @Override
    public List<HechoImputado> getAllHechoImputado() {
        return hechoImputadoRepository.findAll();
    }

    @Override
    public List<HechoImputado> getAllHechosImputado(Integer CI_Hecho){

        List<HechoImputado> salida = hechoImputadoRepository.findAll();
//        List<HechoImputado> salida2 = hechoImputadoRepository.findAll();


//        for (HechoImputado hechoImputado :
//                salida) {
//            Hecho hecho = hechoRepository.findById(CI_Hecho).get();
//            if (!Objects.equals(hecho.getCI_Id(), hechoImputado.getCIHecho())){
//                salida2.remove(hechoImputado);
//            }
//        }

        return salida.stream().filter(hechoImputado -> Objects.equals(hechoImputado.getCIHecho(), CI_Hecho)).collect(Collectors.toList());
    }

    @Override
    public List<HechoImputado> getAllHechoImputados(Integer CI_Imputado){

        List<HechoImputado> salida = hechoImputadoRepository.findAll();

        return salida.stream().filter(hechoImputado -> Objects.equals(hechoImputado.getCIImputado(), CI_Imputado)).collect(Collectors.toList());
    }

    @Override
    public HechoImputado saveHechoImputado(HechoImputado hechoImputado) {
        return hechoImputadoRepository.save(hechoImputado);
    }

    @Override
    public HechoImputado getHechoImputadoById(Integer Id) {
        return hechoImputadoRepository.findById(Id).get();
    }

    @Override
    public HechoImputado updateHechoImputado(HechoImputado hechoImputado) {
        return hechoImputadoRepository.save(hechoImputado);
    }

    @Override
    public void deleteHechoImputadoById(Integer Id) {
        hechoImputadoRepository.deleteById(Id);
    }

    @Override
    public HechoImputado getHechoImputadoByIdHecho(Integer CIHecho) {
        return hechoImputadoRepository.findByCIHecho(CIHecho);
    }

    @Override
    public HechoImputado getHechoImputadoByIdImputado(Integer CIImputado) {
        return hechoImputadoRepository.findByCIImputado(CIImputado);
    }
}
