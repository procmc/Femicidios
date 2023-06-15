package com.if7100.service.impl;

import com.if7100.entity.Modalidad;
import com.if7100.repository.ModalidadRepository;
import com.if7100.service.ModalidadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModalidadServiceImpl implements ModalidadService {

    private ModalidadRepository modalidadRepository;

    public ModalidadServiceImpl(ModalidadRepository modalidadRepository) {
        super();
        this.modalidadRepository = modalidadRepository;
    }

    @Override
    public List<Modalidad> getAllModalidades() {
        return modalidadRepository.findAll();
    }

    @Override
    public Page<Modalidad> getAllModalidadesPage(Pageable pageable){
        return modalidadRepository.findAll(pageable);
    }

    @Override
    public Modalidad saveModalidad(Modalidad modalidad) {
        return modalidadRepository.save(modalidad);
    }

    @Override
    public Modalidad getModalidadById(Integer Id) {
        return modalidadRepository.findById(Id).get();
    }

    @Override
    public Modalidad updateModalidad(Modalidad modalidad) {
        return modalidadRepository.save(modalidad);
    }

    @Override
    public void deleteModalidadById(Integer Id) {
        modalidadRepository.deleteById(Id);
    }

    @Override
    public Modalidad getModalidadByTitulo(String CVTitulo) {
        return modalidadRepository.findByCVTitulo(CVTitulo);
    }

    @Override
    public Modalidad getModalidadByDescripcion(String CVDescripcion) {
        return modalidadRepository.findByCVDescripcion(CVDescripcion);
    }
}
