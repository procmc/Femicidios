package com.if7100.service.impl;

import com.if7100.entity.*;
import com.if7100.repository.*;
import com.if7100.service.HechoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HechoServiceImpl implements HechoService {

    private HechoRepository hechoRepository;

    private ModalidadRepository modalidadRepository;

    private TipoRelacionRepository tipoRelacionRepository;

    private TipoVictimaRepository tipoVictimaRepository;

    private OrganismoRepository organismoRepository;

    private VictimaRepository victimaRepository;

    private ProcesoJudicialRepository procesoJudicialRepository;

    public HechoServiceImpl(HechoRepository hechoRepository, ModalidadRepository modalidadRepository,
                            TipoVictimaRepository tipoVictimaRepository, TipoRelacionRepository tipoRelacionRepository,
                            OrganismoRepository organismoRepository, VictimaRepository victimaRepository, ProcesoJudicialRepository procesoJudicialRepository) {
        super();
        this.hechoRepository = hechoRepository;
        this.modalidadRepository = modalidadRepository;
        this.tipoRelacionRepository = tipoRelacionRepository;
        this.tipoVictimaRepository = tipoVictimaRepository;
        this.organismoRepository = organismoRepository;
        this.victimaRepository = victimaRepository;
        this.procesoJudicialRepository = procesoJudicialRepository;
    }


    @Override
    public List<Hecho> getAllHechos() {
        return hechoRepository.findAll();
    }

    @Override
    public List<Modalidad> getAllModalidades() {

        List<Hecho> hechos = hechoRepository.findAll();
        List<Modalidad> modalidades = new ArrayList<>();

        for (Hecho hecho :
                hechos) {
            modalidades.add(modalidadRepository.findById(hecho.getCIModalidad()).orElse(new Modalidad()));
        }

        return modalidades;
    }

    @Override
    public List<TipoRelacion> getAllTipoRelaciones() {
        List<Hecho> hechos = hechoRepository.findAll();
        List<TipoRelacion> tipoRelaciones = new ArrayList<>();
        TipoRelacion tipoRelacion = new TipoRelacion();
        for (Hecho hecho :
                hechos) {
            tipoRelaciones.add(tipoRelacionRepository.findById(hecho.getCITipoRelacion()).orElse(tipoRelacion));
        }

        return tipoRelaciones;
    }

    @Override
    public List<TipoVictima> getAllTipoVictimas() {
        List<Hecho> hechos = hechoRepository.findAll();
        List<TipoVictima> tipoVictimas = new ArrayList<>();
        TipoVictima tipoVictima = new TipoVictima();
        for (Hecho hecho :
                hechos) {
            tipoVictimas.add(tipoVictimaRepository.findById(hecho.getCITipoVictima()).orElse(tipoVictima));
        }

        return tipoVictimas;
    }

    @Override
    public List<Organismo> getAllOrganismos() {
        List<Hecho> hechos = hechoRepository.findAll();
        List<Organismo> organismos = new ArrayList<>();
        Organismo organismo = new Organismo();
        for (Hecho hecho :
                hechos) {
            organismos.add(organismoRepository.findById(hecho.getCIIdGenerador()).orElse(organismo));
        }

        return organismos;
    }

    @Override
    public List<Victima> getAllVictimas() {
        List<Hecho> hechos = hechoRepository.findAll();
        List<Victima> victimas = new ArrayList<>();
        Victima victima = new Victima();
        for (Hecho hecho :
                hechos) {
            victimas.add(victimaRepository.findById(hecho.getCIIdVictima()).orElse(victima));
        }

        return victimas;
    }

    @Override
    public List<ProcesoJudicial> getAllProcesosJudiciales() {
        List<Hecho> hechos = hechoRepository.findAll();
        List<ProcesoJudicial> procesoJudiciales = new ArrayList<>();
        ProcesoJudicial procesoJudicial = new ProcesoJudicial();
        for (Hecho hecho :
                hechos) {
            procesoJudiciales.add(procesoJudicialRepository.findById(hecho.getCIIdProceso()).orElse(procesoJudicial));
        }

        return procesoJudiciales;
    }


    @Override
    public Hecho saveHecho(Hecho hecho) {
        return hechoRepository.save(hecho);
    }

    @Override
    public Hecho getHechoById(Integer Id) {
        return hechoRepository.findById(Id).get();
    }

    @Override
    public Hecho updateHecho(Hecho hecho) {
        return hechoRepository.save(hecho);
    }

    @Override
    public void deleteHechoById(Integer Id) {
        hechoRepository.deleteById(Id);
    }

    @Override
    public Hecho getHechoByPais(Integer CIPais) {
        return hechoRepository.findByCIPais(CIPais);
    }

    @Override
    public Hecho getHechoByTipoVictima(Integer CITipoVictima) {
        return hechoRepository.findByCITipoVictima(CITipoVictima);
    }

    @Override
    public Hecho getHechoByTipoRelacion(Integer CITipoRelacion) {
        return hechoRepository.findByCITipoRelacion(CITipoRelacion);
    }

    @Override
    public Hecho getHechoByModalidad(Integer CIModalidad) {
        return hechoRepository.findByCIModalidad(CIModalidad);
    }

    @Override
    public Hecho getHechoByCIIdVictima(Integer CIIdVictima) {
        return hechoRepository.findByCIIdVictima(CIIdVictima);
    }

    @Override
    public Hecho getHechoByCIIdProceso(Integer CIIdProceso) {
        return hechoRepository.findByCIIdProceso(CIIdProceso);
    }

    @Override
    public Hecho getHechoByCVAgresionSexual(String CVAgresionSexual) {
        return hechoRepository.findByCVAgresionSexual(CVAgresionSexual);
    }

    @Override
    public Hecho getHechoByCVDenunciaPrevia(String CVDenunciaPrevia) {
        return hechoRepository.findByCVDenunciaPrevia(CVDenunciaPrevia);
    }

    @Override
    public Hecho getHechoByCIIdGenerador(Integer CIIdGenerador) {
        return hechoRepository.findByCIIdGenerador(CIIdGenerador);
    }

    @Override
    public Hecho getHechoByCDFecha(String CDFecha) {
        return hechoRepository.findByCDFecha(CDFecha);
    }
}
