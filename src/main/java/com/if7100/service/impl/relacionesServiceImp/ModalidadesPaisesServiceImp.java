package com.if7100.service.impl.relacionesServiceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.Modalidad;
import com.if7100.entity.relacionesEntity.ModalidadesPaises;
import com.if7100.repository.relacionesRepository.ModalidadesPaisesRepository;
import com.if7100.service.relacionesService.ModalidadesPaisesService;

@Service
public class ModalidadesPaisesServiceImp implements ModalidadesPaisesService {
    
    private final ModalidadesPaisesRepository modalidadesPaisesRepository;

    public ModalidadesPaisesServiceImp(ModalidadesPaisesRepository modalidadesPaisesRepository) {
        this.modalidadesPaisesRepository = modalidadesPaisesRepository;
    }

    @Override
    public List<ModalidadesPaises> getAllRelaciones() {
        return modalidadesPaisesRepository.findAll();
    }

    @Override
    public void deleteRelacionById(Integer id) {
        modalidadesPaisesRepository.deleteById(id);

    }

    @Override
    public ModalidadesPaises saveModalidadesPaises(
            ModalidadesPaises modalidadesPaises) {
        return modalidadesPaisesRepository.save(modalidadesPaises);
    }

    @Override
    public List<ModalidadesPaises> getRelacionesByModalidad(Modalidad modalidad) {
        return modalidadesPaisesRepository.findByModalidad(modalidad);
    }


    
}
