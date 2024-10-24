package com.if7100.entity;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TA_Dependiente")
public class Dependiente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CI_Codigo;

	@Column(name="CV_Dni", nullable =false)
	private String CVDNI;
	
	
    @ManyToOne
    @JoinColumn(name = "CI_Tiporelacion",referencedColumnName = "CI_Codigo", nullable = false)
    private TipoRelacionFamiliar tipoRelacionFamiliar;

	@OneToMany(mappedBy = "dependiente")
    private List<DependienteVictima> dependienteVictimas;

	public Dependiente(  ) {}

	public Dependiente(int CI_Codigo, String cVDNI, TipoRelacionFamiliar tipoRelacionFamiliar) {
		super();
		this.CI_Codigo = CI_Codigo;
		CVDNI = cVDNI;
		this.tipoRelacionFamiliar = tipoRelacionFamiliar;
	}

	public int getCI_Codigo() {
		return CI_Codigo;
	}

	public void setCI_Codigo(int CI_Codigo) {
		this.CI_Codigo = CI_Codigo;
	}

	public String getCVDNI() {
		return CVDNI;
	}

	public void setCVDNI(String cVDNI) {
		CVDNI = cVDNI;
	}

	public TipoRelacionFamiliar getTipoRelacionFamiliar() {
		return tipoRelacionFamiliar;
	}

	public void setTipoRelacionFamiliar(TipoRelacionFamiliar tipoRelacionFamiliar) {
		this.tipoRelacionFamiliar = tipoRelacionFamiliar;
	}

}