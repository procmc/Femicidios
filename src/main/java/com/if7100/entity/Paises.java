package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ta_codigopaises")
public class Paises {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@Column(name = "spanish", nullable = false)
	private String spanish; // nombre del país en español
	@Column(name = "english", nullable = false)
	private String english; // nombre del país en ingles
	@Column(name = "ISO2", nullable = true)
	private String ISO2; // las acortaciones de país en dos letras
	@Column(name = "ISO3", nullable = true)
	private String ISO3; // las acortaciones de país en tres letras : ejemplo: CRC= Costa Rica
	@Column(name = "telefonopais", nullable = true)
	private String telefonoPais;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getSpanish() {
		return spanish;
	}

	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getISO2() {
		return ISO2;
	}

	public void setISO2(String iSO2) {
		ISO2 = iSO2;
	}

	public String getISO3() {
		return ISO3;
	}

	public void setISO3(String iSO3) {
		ISO3 = iSO3;
	}

	public String getTelefonoPais() {
		return telefonoPais;
	}

	public void setTelefonoPais(String telefonoPais) {
		this.telefonoPais = telefonoPais;
	}
	public Paises() {
		
	}
	public Paises(String spanish, String english, String iSO2, String iSO3, String telefonoPais) {
		super();
		this.spanish = spanish;
		this.english = english;
		ISO2 = iSO2;
		ISO3 = iSO3;
		this.telefonoPais = telefonoPais;
	}

}