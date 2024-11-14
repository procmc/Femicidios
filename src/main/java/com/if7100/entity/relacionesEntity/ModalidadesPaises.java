package com.if7100.entity.relacionesEntity;

import com.if7100.entity.Modalidad;
import com.if7100.entity.Paises;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_modalidadespais")
public class ModalidadesPaises {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_modalidadespais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_modalidades")
    private Modalidad modalidad;

    // Getters y Setters

    public Integer getId_modalidadespais() {
        return id_modalidadespais;
    }

    public void setId_modalidadespais(Integer id_modalidadespais) {
        this.id_modalidadespais = id_modalidadespais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

}
