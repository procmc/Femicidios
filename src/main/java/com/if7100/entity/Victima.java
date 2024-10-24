package com.if7100.entity;

import java.util.List;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TA_Victima")
public class Victima {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CI_Id;

	@Column(name="CV_Dni", nullable =false)
	private String CVDNI;
	
	@Column(name="CV_Nombre", nullable =false)
	private String CVNombre ;
	
	@Column(name="CV_Apellidopaterno", nullable =false)
	private String CVApellidoPaterno;
	
	@Column(name="CV_Apellidomaterno", nullable =false)
	private String CVApellidoMaterno;
	
	@Column(name="CI_Edad", nullable =false)
	private int CIEdad;
	
	@Column(name="CV_Idgenero", nullable =false)
	private int CVGenero;
	
	@Column(name="CV_Lugarnac", nullable =false)
	private String CVLugarNac;
	
	@Column(name="CV_Orientasex", nullable =false)
	private int CVOrientaSex;
	
	@Column(name="CV_Nacionalidad", nullable =false)
	private String CVNacionalidad;
	
	@Column(name="CI_Educacion", nullable =false)
	private int CIEducacion;
	
	@Column(name="CV_Ocupacion", nullable =false)
	private String CVOcupacion;
	
	@Column(name="CV_Domicilio", nullable =false)
	private String CVDomicilio;
	
	@Column(name="CV_LugarResidencia", nullable =false)
	private String CVLugarResidencia;
	
	@Column(name="CV_Discapacidad", nullable =false)
	private String CVDiscapacidad;
	
	@Column(name="CV_CondicionMigratoria", nullable =false)
	private String CVCondicionMigratoria;
	
	@Column(name="CV_Etnia", nullable =false)
	private String CVEtnia;
	
	@Column(name="CV_MedidasProteccion", nullable =false)
	private String CVMedidasProteccion;
	
	@Column(name="CV_DenunciasPrevias", nullable =false)
	private String CVDenunciasPrevias;
	
	@Column(name="CI_Hijos", nullable =false)
	private int CIHijos;
	
	@Column(name="CV_Generador", nullable =false)
	private String CVGenerador;

	@Column(name="codigo_pais", nullable =false)
	private Integer CICodigoPais;

	@OneToMany(mappedBy = "victima")
    private List<Hecho> hechos;
	
	public Victima() {
		
	}


	public Victima(String cVDNI, String cVNombre, String cVApellidoPaterno, String cVApellidoMaterno,
			int cIEdad, int cVGenero, String cVLugarNac, int cVOrientaSex, String CVNacionalidad, 
			int CIEducacion, String CVOcupacion, String CVDomicilio, String CVLugarResidencia, 
			String CVDiscapacidad, String CVCondicionMigratoria, String CVEtnia, String CVMedidasProteccion, 
			String CVDenunciasPrevias, int CIHijos, String CVGenerador, Integer CICodigoPais) {
		super();
		
		CVDNI = cVDNI;
		CVNombre = cVNombre;
		CVApellidoPaterno = cVApellidoPaterno;
		CVApellidoMaterno = cVApellidoMaterno;
		CIEdad = cIEdad;
		CVGenero = cVGenero;
		CVLugarNac = cVLugarNac;
		CVOrientaSex = cVOrientaSex;
		this.CVNacionalidad = CVNacionalidad;
		this.CIEducacion = CIEducacion;
		this.CVOcupacion = CVOcupacion;
		this.CVDomicilio = CVDomicilio;
		this.CVLugarResidencia = CVLugarResidencia;
		this.CVDiscapacidad = CVDiscapacidad;
		this.CVCondicionMigratoria = CVCondicionMigratoria;
		this.CVEtnia = CVEtnia;
		this.CVMedidasProteccion = CVMedidasProteccion;
		this.CVDenunciasPrevias = CVDenunciasPrevias;
		this.CIHijos = CIHijos;
		this.CVGenerador = CVGenerador;		
		this.CICodigoPais = CICodigoPais;
		
	}

	public int getCICodigoPais() {
		return CICodigoPais;
	}


	public void setCICodigoPais(int CICodigoPais) {
		this.CICodigoPais = CICodigoPais;
	}

	public int getCI_Id() {
		return CI_Id;
	}


	public void setCI_Id(int cI_Id) {
		CI_Id = cI_Id;
	}


	public String getCVDNI() {
		return CVDNI;
	}


	public void setCVDNI(String cVDNI) {
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


	public int getCIEdad() {
		return CIEdad;
	}


	public void setCVEdad(int cIEdad) {
		CIEdad = cIEdad;
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


	/**
	 * @return the cVNacionalidad
	 */
	public String getCVNacionalidad() {
		return CVNacionalidad;
	}


	/**
	 * @param cVNacionalidad the cVNacionalidad to set
	 */
	public void setCVNacionalidad(String cVNacionalidad) {
		CVNacionalidad = cVNacionalidad;
	}


	/**
	 * @return the cIEducacion
	 */
	public int getCIEducacion() {
		return CIEducacion;
	}


	/**
	 * @param cIEducacion the cIEducacion to set
	 */
	public void setCIEducacion(int cIEducacion) {
		CIEducacion = cIEducacion;
	}


	/**
	 * @return the cVOcupacion
	 */
	public String getCVOcupacion() {
		return CVOcupacion;
	}


	/**
	 * @param cVOcupacion the cVOcupacion to set
	 */
	public void setCVOcupacion(String cVOcupacion) {
		CVOcupacion = cVOcupacion;
	}


	/**
	 * @return the cVDomicilio
	 */
	public String getCVDomicilio() {
		return CVDomicilio;
	}


	/**
	 * @param cVDomicilio the cVDomicilio to set
	 */
	public void setCVDomicilio(String cVDomicilio) {
		CVDomicilio = cVDomicilio;
	}


	/**
	 * @return the cVLugarResidencia
	 */
	public String getCVLugarResidencia() {
		return CVLugarResidencia;
	}


	/**
	 * @param cVLugarResidencia the cVLugarResidencia to set
	 */
	public void setCVLugarResidencia(String cVLugarResidencia) {
		CVLugarResidencia = cVLugarResidencia;
	}


	/**
	 * @return the cVDiscapacidad
	 */
	public String getCVDiscapacidad() {
		return CVDiscapacidad;
	}


	/**
	 * @param cVDiscapacidad the cVDiscapacidad to set
	 */
	public void setCVDiscapacidad(String cVDiscapacidad) {
		CVDiscapacidad = cVDiscapacidad;
	}


	/**
	 * @return the cVCondicionMigratoria
	 */
	public String getCVCondicionMigratoria() {
		return CVCondicionMigratoria;
	}


	/**
	 * @param cVCondicionMigratoria the cVCondicionMigratoria to set
	 */
	public void setCVCondicionMigratoria(String cVCondicionMigratoria) {
		CVCondicionMigratoria = cVCondicionMigratoria;
	}


	/**
	 * @return the cVEtnia
	 */
	public String getCVEtnia() {
		return CVEtnia;
	}


	/**
	 * @param cVEtnia the cVEtnia to set
	 */
	public void setCVEtnia(String cVEtnia) {
		CVEtnia = cVEtnia;
	}


	/**
	 * @return the cVMedidasProteccion
	 */
	public String getCVMedidasProteccion() {
		return CVMedidasProteccion;
	}


	/**
	 * @param cVMedidasProteccion the cVMedidasProteccion to set
	 */
	public void setCVMedidasProteccion(String cVMedidasProteccion) {
		CVMedidasProteccion = cVMedidasProteccion;
	}


	/**
	 * @return the cVDenunciasPrevias
	 */
	public String getCVDenunciasPrevias() {
		return CVDenunciasPrevias;
	}


	/**
	 * @param cVDenunciasPrevias the cVDenunciasPrevias to set
	 */
	public void setCVDenunciasPrevias(String cVDenunciasPrevias) {
		CVDenunciasPrevias = cVDenunciasPrevias;
	}


	/**
	 * @return the cIHijos
	 */
	public int getCIHijos() {
		return CIHijos;
	}


	/**
	 * @param cIHijos the cIHijos to set
	 */
	public void setCIHijos(int cIHijos) {
		CIHijos = cIHijos;
	}


	/**
	 * @return the cVGenerador
	 */
	public String getCVGenerador() {
		return CVGenerador;
	}


	/**
	 * @param cVGenerador the cVGenerador to set
	 */
	public void setCVGenerador(String cVGenerador) {
		CVGenerador = cVGenerador;
	}

	//relacion entre victima y hechos
	public List<Hecho> getHechos() {
		return hechos;
	}
	
	public void setHechos(List<Hecho> hechos) {
		this.hechos = hechos;
	}

}
