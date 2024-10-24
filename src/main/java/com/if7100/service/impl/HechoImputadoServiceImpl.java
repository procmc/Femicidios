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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<HechoImputado> getAllHechoImputadoPage(Pageable pageable){
        return hechoImputadoRepository.findAll(pageable);
    }

    @Override
    public List<HechoImputado> getAllHechosImputado(Integer CI_Hecho){

        List<HechoImputado> salida = hechoImputadoRepository.findAll();
        return salida.stream().filter(hechoImputado -> Objects.equals(hechoImputado.getCIHecho(), CI_Hecho)).collect(Collectors.toList());
    }

    @Override
    public Page<HechoImputado> getAllHechosImputadoPage(Pageable pageable ,Integer CI_Hecho){

        List<HechoImputado> salida = hechoImputadoRepository.findAll();
        salida = salida.stream().filter(hechoImputado -> Objects.equals(hechoImputado.getCIHecho(), CI_Hecho)).collect(Collectors.toList());

        return new PageImpl<>(salida, pageable, salida.size());

    }

    @Override
    public List<HechoImputado> getAllHechoImputados(Integer CI_Imputado){

        List<HechoImputado> salida = hechoImputadoRepository.findAll();

        return salida.stream().filter(hechoImputado -> Objects.equals(hechoImputado.getCIImputado(), CI_Imputado)).collect(Collectors.toList());
    }

    @Override
    public Page<HechoImputado> getAllHechoImputadosPage(Pageable pageable ,Integer CI_Imputado){

        List<HechoImputado> salida = hechoImputadoRepository.findAll();
        salida = salida.stream().filter(hechoImputado -> Objects.equals(hechoImputado.getCIImputado(), CI_Imputado)).collect(Collectors.toList());

        return new PageImpl<>(salida, pageable, salida.size());

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
    public List<HechoImputado> getHechoImputadoByIdHecho(Integer CIHecho) {
        return hechoImputadoRepository.findAllByCIHecho(CIHecho);
    }

    @Override
    public List<HechoImputado> getHechoImputadoByIdImputado(Integer CIImputado) {
        return hechoImputadoRepository.findAllByCIImputado(CIImputado);
    }
}
