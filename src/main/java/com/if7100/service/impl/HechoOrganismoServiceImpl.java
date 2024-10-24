package com.if7100.service.impl;

import com.if7100.entity.HechoOrganismo;
import com.if7100.repository.HechoOrganismoRepository;
import com.if7100.repository.HechoRepository;
import com.if7100.repository.OrganismoRepository;
import com.if7100.service.HechoOrganismoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HechoOrganismoServiceImpl implements HechoOrganismoService {

    private HechoOrganismoRepository hechoOrganismoRepository;

    private HechoRepository hechoRepository;

    private OrganismoRepository organismoRepository;

    public HechoOrganismoServiceImpl(HechoOrganismoRepository hechoOrganismoRepository ,HechoRepository hechoRepository, OrganismoRepository organismoRepository){
        super();
        this.hechoOrganismoRepository = hechoOrganismoRepository;
        this.hechoRepository = hechoRepository;
        this.organismoRepository = organismoRepository;
    }

    @Override
    public List<HechoOrganismo> getAllHechoOrganismo() {
        return hechoOrganismoRepository.findAll();
    }

    @Override
    public Page<HechoOrganismo> getAllHechoOrganismoPage(Pageable pageable){
        return hechoOrganismoRepository.findAll(pageable);
    }

    @Override
    public List<HechoOrganismo> getAllHechosOrganismo(Integer CI_Hecho) {
        List<HechoOrganismo> salida = hechoOrganismoRepository.findAll();

        return salida.stream().filter(hechoOrganismo -> Objects.equals(hechoOrganismo.getCIHecho(), CI_Hecho)).collect(Collectors.toList());
    }

    @Override
    public Page<HechoOrganismo> getAllHechosOrganismoPage(Pageable pageable, Integer CI_Hecho){
        List<HechoOrganismo> salida = hechoOrganismoRepository.findAll();
        salida = salida.stream().filter(hechoOrganismo -> Objects.equals(hechoOrganismo.getCIHecho(), CI_Hecho)).collect(Collectors.toList());

        return new PageImpl<>(salida, pageable, salida.size());
    }

    @Override
    public List<HechoOrganismo> getAllHechoOrganismos(Integer CI_Organismo) {
        List<HechoOrganismo> salida = hechoOrganismoRepository.findAll();

        return salida.stream().filter(hechoOrganismo -> Objects.equals(hechoOrganismo.getCIOrganismo(), CI_Organismo)).collect(Collectors.toList());
    }

    @Override
    public Page<HechoOrganismo> getAllHechoOrganismosPage(Pageable pageable, Integer CI_Organismo){
        List<HechoOrganismo> salida = hechoOrganismoRepository.findAll();
        salida = salida.stream().filter(hechoOrganismo -> Objects.equals(hechoOrganismo.getCIOrganismo(), CI_Organismo)).collect(Collectors.toList());
        return new PageImpl<>(salida, pageable, salida.size());
    }

    @Override
    public HechoOrganismo saveHechoOrganismo(HechoOrganismo hechoOrganismo) {
        return hechoOrganismoRepository.save(hechoOrganismo);
    }

    @Override
    public HechoOrganismo getHechoOrganismoById(Integer Id) {
        return hechoOrganismoRepository.findById(Id).get();
    }

    @Override
    public HechoOrganismo updateHechoOrganismo(HechoOrganismo hechoOrganismo) {
        return hechoOrganismoRepository.save(hechoOrganismo);
    }

    @Override
    public void deleteHechoOrganismoById(Integer Id) {
        hechoOrganismoRepository.deleteById(Id);
    }

    @Override
    public List<HechoOrganismo> getHechoOrganismoByIdCIHecho(Integer CIHecho) {
        return hechoOrganismoRepository.findAllByCIHecho(CIHecho);
    }

    @Override
    public List<HechoOrganismo> getHechoOrganismoBYIdCIOrganismo(Integer CIOrganismo) {
        return hechoOrganismoRepository.findAllByCIOrganismo(CIOrganismo);
    }
}
