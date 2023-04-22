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
	private int CI_DNI;
	
	@Column(name="CV_Nombre", nullable =false)
	private String CV_Nombre ;
	
	@Column(name="CV_ApellidoPaterno", nullable =false)
	private String CV_ApellidoPaterno;
	
	@Column(name="CV_ApellidoMaterno", nullable =false)
	private String CV_ApellidoMaterno;
	
	@Column(name="CV_Edad", nullable =false)
	private int CV_Edad;
	
	@Column(name="CV_Genero", nullable =false)
	private int CV_Genero;
	
	@Column(name="CV_LugarNac", nullable =false)
	private String CV_LugarNac;
	
	@Column(name="CV_OrientaSex", nullable =false)
	private int CV_OrientaSex;
	
	
	public Victima() {
		
	}

	public Victima(int cI_DNI, String cV_Nombre, String cV_ApellidoPaterno, String cV_ApellidoMaterno, int cV_Edad,
			int cV_Genero, String cV_LugarNac, int cV_OrientaSex) {
		super();
		CI_DNI = cI_DNI;
		CV_Nombre = cV_Nombre;
		CV_ApellidoPaterno = cV_ApellidoPaterno;
		CV_ApellidoMaterno = cV_ApellidoMaterno;
		CV_Edad = cV_Edad;
		CV_Genero = cV_Genero;
		CV_LugarNac = cV_LugarNac;
		CV_OrientaSex = cV_OrientaSex;
	}

	public int getCI_DNI() {
		return CI_DNI;
	}

	public void setCI_DNI(int cI_DNI) {
		CI_DNI = cI_DNI;
	}

	public String getCV_Nombre() {
		return CV_Nombre;
	}

	public void setCV_Nombre(String cV_Nombre) {
		CV_Nombre = cV_Nombre;
	}

	public String getCV_ApellidoPaterno() {
		return CV_ApellidoPaterno;
	}

	public void setCV_ApellidoPaterno(String cV_ApellidoPaterno) {
		CV_ApellidoPaterno = cV_ApellidoPaterno;
	}

	public String getCV_ApellidoMaterno() {
		return CV_ApellidoMaterno;
	}

	public void setCV_ApellidoMaterno(String cV_ApellidoMaterno) {
		CV_ApellidoMaterno = cV_ApellidoMaterno;
	}

	public int getCV_Edad() {
		return CV_Edad;
	}

	public void setCV_Edad(int cV_Edad) {
		CV_Edad = cV_Edad;
	}

	public int getCV_Genero() {
		return CV_Genero;
	}

	public void setCV_Genero(int cV_Genero) {
		CV_Genero = cV_Genero;
	}

	public String getCV_LugarNac() {
		return CV_LugarNac;
	}

	public void setCV_LugarNac(String cV_LugarNac) {
		CV_LugarNac = cV_LugarNac;
	}

	public int getCV_OrientaSex() {
		return CV_OrientaSex;
	}

	public void setCV_OrientaSex(int cV_OrientaSex) {
		CV_OrientaSex = cV_OrientaSex;
	}
	
	

}
