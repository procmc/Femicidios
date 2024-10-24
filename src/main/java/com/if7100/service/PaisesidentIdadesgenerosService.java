package com.if7100.service;

import java.util.List;
import com.if7100.entity.PaisesidentIdadesgeneros;

public interface PaisesidentIdadesgenerosService {
    void save(PaisesidentIdadesgeneros relacion);
    List<PaisesidentIdadesgeneros> getAllRelaciones();
    void deleteRelacionById(Long id);
}
