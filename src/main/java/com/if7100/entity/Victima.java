package com.if7100.entity;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name="TA_Victima")
public class Victima {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CI_Id;

	@Column(name="CV_Dni", nullable =false)
	private int CVDNI;
	
	@Column(name="CV_Nombre", nullable =false)
	private String CVNombre ;
	
	@Column(name="CV_Apellidopaterno", nullable =false)
	private String CVApellidoPaterno;
	
	@Column(name="CV_Apellidomaterno", nullable =false)
	private String CVApellidoMaterno;
	
	@Column(name="CV_Edad", nullable =false)
	private int CVEdad;
	
	@Column(name="CV_Idgenero", nullable =false)
	private int CVGenero;
	
	@Column(name="CV_Lugarnac", nullable =false)
	private String CVLugarNac;
	
	@Column(name="CV_Orientasex", nullable =false)
	private int CVOrientaSex;
	
	
	public Victima() {
		
	}


	public Victima(int cVDNI, String cVNombre, String cVApellidoPaterno, String cVApellidoMaterno,
			int cVEdad, int cVGenero, String cVLugarNac, int cVOrientaSex) {
		super();
		
		CVDNI = cVDNI;
		CVNombre = cVNombre;
		CVApellidoPaterno = cVApellidoPaterno;
		CVApellidoMaterno = cVApellidoMaterno;
		CVEdad = cVEdad;
		CVGenero = cVGenero;
		CVLugarNac = cVLugarNac;
		CVOrientaSex = cVOrientaSex;
	}


	public int getCI_Id() {
		return CI_Id;
	}


	public void setCI_Id(int cI_Id) {
		CI_Id = cI_Id;
	}


	public int getCVDNI() {
		return CVDNI;
	}


	public void setCVDNI(int cVDNI) {
		CVDNI = cVDNI;
	}


	public String getCVNombre() {
		return CVNombre;
	}


	public void setCVNombre(String cVNombre) {
		CVNombre = cVNombre;
	}


	public String getCVApellidoPaterno() {
		return CVApellidoPaterno;
	}


	public void setCVApellidoPaterno(String cVApellidoPaterno) {
		CVApellidoPaterno = cVApellidoPaterno;
	}


	public String getCVApellidoMaterno() {
		return CVApellidoMaterno;
	}


	public void setCVApellidoMaterno(String cVApellidoMaterno) {
		CVApellidoMaterno = cVApellidoMaterno;
	}


	public int getCVEdad() {
		return CVEdad;
	}


	public void setCVEdad(int cVEdad) {
		CVEdad = cVEdad;
	}


	public int getCVGenero() {
		return CVGenero;
	}


	public void setCVGenero(int cVGenero) {
		CVGenero = cVGenero;
	}


	public String getCVLugarNac() {
		return CVLugarNac;
	}


	public void setCVLugarNac(String cVLugarNac) {
		CVLugarNac = cVLugarNac;
	}


	public int getCVOrientaSex() {
		return CVOrientaSex;
	}


	public void setCVOrientaSex(int cVOrientaSex) {
		CVOrientaSex = cVOrientaSex;
	}


	

	

}
