package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "identidadgenero")
public class IdentidadGenero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@Column(name = "cedula", nullable = false)
	private String cedula;

	@Column(name = "genero", nullable = false)
	private String genero;

	@Column(name = "codigopais", nullable = false)
	private Integer codigoPais;

	public IdentidadGenero() {
		// TODO Auto-generated constructor stub
	}

	public IdentidadGenero(String cedula, String genero, int codigoPais) {
		super();
		this.cedula = cedula;
		this.genero = genero;
		this.codigoPais = codigoPais;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(Integer codigoPais) {
		this.codigoPais = codigoPais;
	}

}
