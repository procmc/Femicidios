package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ta_tiporelacionfamiliar")
public class TipoRelacionFamiliar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CI_Codigo;
	@Column(name = "CV_Titulo", nullable = false)
	private String nombre;
	@Column(name = "CV_Descripcion", nullable = false)
	private String descripcion;

	public TipoRelacionFamiliar() {
		// TODO Auto-generated constructor stub
	}

	public TipoRelacionFamiliar(String nombre,String descripcion ) {
		super();
		this.nombre=nombre;
		this.descripcion=descripcion;
	}
	public Integer getCI_Codigo() {
		return CI_Codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCI_Codigo(Integer id) {
		CI_Codigo = id;
	}

}
