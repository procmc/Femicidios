package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TA_Hechos")
public class Hecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CI_Pais", nullable = false)
    private Integer CIPais;

    @Column(name = "CI_Tipo_Victima", nullable = false)
    private Integer CITipoVictima;

    @Column(name = "CI_Tipo_Relacion", nullable = false)
    private Integer CITipoRelacion;

    @Column(name = "CI_Modalidad", nullable = false)
    private Integer CIModalidad;

    public Hecho(){}

    public Hecho(Integer CIPais, Integer CITipoVictima, Integer CITipoRelacion, Integer CIModalidad) {
        super();
        this.CIPais = CIPais;
        this.CITipoVictima = CITipoVictima;
        this.CITipoRelacion = CITipoRelacion;
        this.CIModalidad = CIModalidad;
    }

    public Integer getCI_Id() {
        return CI_Id;
    }

    public void setCI_Id(Integer CI_Id) {
        this.CI_Id = CI_Id;
    }

    public Integer getCIPais() {
        return CIPais;
    }

    public void setCIPais(Integer CVPais) {
        this.CIPais = CVPais;
    }

    public Integer getCITipoVictima() {
        return CITipoVictima;
    }

    public void setCITipoVictima(Integer CITipoVictima) {
        this.CITipoVictima = CITipoVictima;
    }

    public Integer getCITipoRelacion() {
        return CITipoRelacion;
    }

    public void setCITipoRelacion(Integer CITipoRelacion) {
        this.CITipoRelacion = CITipoRelacion;
    }

    public Integer getCIModalidad() {
        return CIModalidad;
    }

    public void setCIModalidad(Integer CIModalidad) {
        this.CIModalidad = CIModalidad;
    }
}
