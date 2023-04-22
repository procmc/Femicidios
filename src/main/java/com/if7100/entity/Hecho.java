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

    @Column(name = "CV_Lugar", nullable = false)
    private Integer CVLugar;

    @Column(name = "CV_Tipo_Victima", nullable = false)
    private Integer CVTipoVictima;

    @Column(name = "CV_Tipo_Relacion", nullable = false)
    private Integer CVTipoRelacion;

    @Column(name = "CV_Modalidad", nullable = false)
    private Integer CVModalidad;

    public Hecho(){}

    public Hecho(Integer CVLugar, Integer CVTipoVictima, Integer CVTipoRelacion, Integer CVModalidad) {
        super();
        this.CVLugar = CVLugar;
        this.CVTipoVictima = CVTipoVictima;
        this.CVTipoRelacion = CVTipoRelacion;
        this.CVModalidad = CVModalidad;
    }

    public Integer getCI_Id() {
        return CI_Id;
    }

    public void setCI_Id(Integer CI_Id) {
        this.CI_Id = CI_Id;
    }

    public Integer getCVLugar() {
        return CVLugar;
    }

    public void setCVLugar(Integer CVLugar) {
        this.CVLugar = CVLugar;
    }

    public Integer getCVTipoVictima() {
        return CVTipoVictima;
    }

    public void setCVTipoVictima(Integer CVTipoVictima) {
        this.CVTipoVictima = CVTipoVictima;
    }

    public Integer getCVTipoRelacion() {
        return CVTipoRelacion;
    }

    public void setCVTipoRelacion(Integer CVTipoRelacion) {
        this.CVTipoRelacion = CVTipoRelacion;
    }

    public Integer getCVModalidad() {
        return CVModalidad;
    }

    public void setCVModalidad(Integer CVModalidad) {
        this.CVModalidad = CVModalidad;
    }
}
