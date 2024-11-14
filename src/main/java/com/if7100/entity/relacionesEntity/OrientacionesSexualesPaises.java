package com.if7100.entity.relacionesEntity;

import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.Paises;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_orientacionessexualespais")
public class OrientacionesSexualesPaises {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_orientacionessexualespais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_orientacionsexual")
    private OrientacionSexual orientacionSexual;

    // Getters y Setters

    public Integer getId_orientacionessexualespais() {
        return id_orientacionessexualespais;
    }

    public void setId_orientacionessexualespais(Integer id_orientacionessexualespais) {
        this.id_orientacionessexualespais = id_orientacionessexualespais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public OrientacionSexual getOrientacionSexual() {
        return orientacionSexual;
    }

    public void setOrientacionSexual(OrientacionSexual orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }
}
