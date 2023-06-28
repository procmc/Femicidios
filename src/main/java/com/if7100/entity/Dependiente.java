package com.if7100.entity;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name="TA_Dependiente")
public class Dependiente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CI_Codigo;

	@Column(name="CV_Dni", nullable =false)
	private String CVDNI;
	
	@Column(name="CI_Tiporelacion", nullable =false)
	private int CI_Tiporelacion;
	
	public Dependiente(  ) {}

	public Dependiente(int cI_Codigo, String cVDNI, int cI_Tiporelacion) {
		super();
		CI_Codigo = cI_Codigo;
		CVDNI = cVDNI;
		CI_Tiporelacion = cI_Tiporelacion;
	}

	public int getCI_Codigo() {
		return CI_Codigo;
	}

	public void setCI_Codigo(int cI_Codigo) {
		CI_Codigo = cI_Codigo;
	}

	public String getCVDNI() {
		return CVDNI;
	}

	public void setCVDNI(String cVDNI) {
		CVDNI = cVDNI;
	}

	public int getCI_Tiporelacion() {
		return CI_Tiporelacion;
	}

	public void setCI_Tiporelacion(int cI_Tiporelacion) {
		CI_Tiporelacion = cI_Tiporelacion;
	}
	
	

}