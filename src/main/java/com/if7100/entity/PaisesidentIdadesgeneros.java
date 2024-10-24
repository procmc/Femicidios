package com.if7100.entity;

import java.util.Optional;

import jakarta.persistence.*;

@Entity
@Table(name = "ta_paisesidentidadesgeneros")
public class PaisesidentIdadesgeneros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codigo_pais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "codigo_identidadgenero")
    private IdentidadGenero identidadGenero;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais2) {
        this.pais = pais2;
    }

    public IdentidadGenero getIdentidadGenero() {
        return identidadGenero;
    }

    public void setIdentidadGenero(IdentidadGenero identidadGenero) {
        this.identidadGenero = identidadGenero;
    }
}
