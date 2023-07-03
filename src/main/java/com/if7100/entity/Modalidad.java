package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TA_Modalidades")
//Esta es la clase de campos codificados
public class Modalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Codigo;

    @Column(name = "CV_Titulo", nullable = false)
    private String CVTitulo;

    @Column(name = "CV_Descripcion", nullable = false)
    private String CVDescripcion;
    
    @Column(name = "CV_Pais", nullable = false)
    private String CVPais;

    public Modalidad() {
    }

    public Modalidad(Integer CI_Codigo, String CVTitulo, String CVDescripcion, String CV_Pais) {
        this.CI_Codigo = CI_Codigo;
        this.CVTitulo = CVTitulo;
        this.CVDescripcion = CVDescripcion;
        this.CVPais= CVPais;
    }

    public Integer getCI_Codigo() {
        return CI_Codigo;
    }

    public void setCI_Codigo(Integer CI_Codigo) {
        this.CI_Codigo = CI_Codigo;
    }

    public String getCVTitulo() {
        return CVTitulo;
    }

    public void setCVTitulo(String CV_Titulo) {
        this.CVTitulo = CV_Titulo;
    }

    public String getCVDescripcion() {
        return CVDescripcion;
    }

    public void setCVDescripcion(String CV_Descripcion) {
        this.CVDescripcion = CV_Descripcion;
    }

	public String getCVPais() {
		return CVPais;
	}

	public void setCVPais(String cVPais) {
		CVPais = cVPais;
	}
    
}
