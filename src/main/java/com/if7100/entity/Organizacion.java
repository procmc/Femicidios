package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ta_organizacion")
public class Organizacion {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CI_Codigo_Organizacion;

	@Column(name = "CV_Nombre", nullable = false)
	private String CVNombre;
    
    @Column(name = "CV_Direccion", nullable = false)
	private String CVDireccion;

    @Column(name = "CV_Telefono", nullable = false)
	private String CVTelefono;

    @Column(name = "CV_Correo", nullable = false)
	private String CVCorreo;

    @Column(name = "CI_Codigo_Pais", nullable = false)
	private Integer codigoPais;

    public Organizacion() {}

	public Organizacion(String CVNombre,String CVDireccion, String CVTelefono, String CVCorreo, int codigoPais) {
		super();
		this.CVNombre=CVNombre;
		this.CVDireccion=CVDireccion;
        this.CVTelefono=CVTelefono;
        this.CVCorreo=CVCorreo;
        this.codigoPais=codigoPais;
	}

    public Integer getCI_Codigo_Organizacion() {
		return CI_Codigo_Organizacion;
	}

	public void setCI_Codigo_Organizacion(Integer CI_Codigo_Organizacion) {
		this.CI_Codigo_Organizacion = CI_Codigo_Organizacion;
	}

    public String getCVNombre() {
		return CVNombre;
	}

	public void setCVNombre(String CVNombre) {
		this.CVNombre = CVNombre;
	}

    public String getCVDireccion() {
		return CVDireccion;
	}

	public void setCVDireccion(String CVDireccion) {
		this.CVDireccion = CVDireccion;
	}

    public String getCVTelefono() {
		return CVTelefono;
	}

	public void setCVTelefono(String CVTelefono) {
		this.CVTelefono = CVTelefono;
	}

    public String getCVCorreo() {
		return CVCorreo;
	}

	public void setCVCorreo(String CVCorreo) {
		this.CVCorreo = CVCorreo;
	}

     public Integer getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(Integer codigoPais) {
		this.codigoPais = codigoPais;
	}
}
