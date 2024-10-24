/**
 * 
 */
package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Kijan
 *
 */
@Entity
@Table(name = "ta_imputados")
public class Imputado {

	/**
	 * 
	 */
	public Imputado() {
		// TODO Auto-generated constructor stub
	}

	public Imputado(String cV_DNI, String cVNombre, String cVGenero, String cVOrientacionSexual, char CVSexo,
			String cIEdad,
			String cVLugarNacimiento, int codigoPais) {
		super();
		this.CVDni = cV_DNI;
		this.CVNombre = cVNombre;
		this.CVGenero = cVGenero;
		this.CVOrientacionSexual = cVOrientacionSexual;
		this.CVSexo = CVSexo;
		this.CIEdad = cIEdad;
		this.CVLugarNacimiento = cVLugarNacimiento;
		this.codigoPais = codigoPais;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CI_Id;

	public int getCI_Id() {
		return CI_Id;
	}

	public void setCI_Id(int cI_Id) {
		CI_Id = cI_Id;
	}

	@Column(name = "cv_dni", nullable = false)
	private String CVDni;

	@Column(name = "cv_nombre", nullable = false)
	private String CVNombre;

	@Column(name = "cv_genero", nullable = false)
	private String CVGenero;

	@Column(name = "cv_OrientacionSexual", nullable = false)
	private String CVOrientacionSexual;

	@Column(name = "cv_sexo", nullable = false)
	private char CVSexo;

	@Column(name = "ci_edad", nullable = false)
	private String CIEdad;

	@Column(name = "CV_LugarNacimiento", nullable = false)
	private String CVLugarNacimiento;

	@Column(name = "CV_Nacionalidad", nullable = false)
	private String CVNacionalidad;

	@Column(name = "CV_Educacion", nullable = false)
	private String CVEducacion;

	@Column(name = "CV_ocupacion", nullable = false)
	private String CVOcupacion;

	@Column(name = "CV_domicilio", nullable = false)
	private String CVDomicilio;

	@Column(name = "CV_LugarResidencia", nullable = false)
	private String CVLugarResidencia;

	@Column(name = "CV_CondicionMigratoria", nullable = false)
	private String CVCondicionMigratoria;

	@Column(name = "CV_Etnia", nullable = false)
	private String CVEtnia;

	@Column(name = "CV_SituacionJuridica", nullable = false)
	private String CVSituacionJuridica;

	@Column(name = "CV_EstadoConyugal", nullable = false)
	private String CVEstadoConyugal;

	@Column(name = "CV_PermisoPortacionArmas", nullable = false)
	private String CVPermisoPortacionArmas;

	@Column(name = "CV_PertenenciaFuerzaSeguridad", nullable = false)
	private String CVPertenenciaFuerzaSeguridad;

	@Column(name = "CV_Antecedentes", nullable = false)
	private String CVAntecedentes;

	@Column(name = "CV_Suicidio", nullable = false)
	private String CVSuicidio;

	@Column(name = "CV_Generador", nullable = false)
	private String CVGenerador;

	@Column(name = "codigo_pais", nullable = false)
	private Integer codigoPais;

	public String getCVNacionalidad() {
		return CVNacionalidad;
	}

	public void setCVNacionalidad(String CVNacionalidad) {
		this.CVNacionalidad = CVNacionalidad;
	}

	public String getCVEducacion() {
		return CVEducacion;
	}

	public void setCVEducacion(String CVEducacion) {
		this.CVEducacion = CVEducacion;
	}

	public String getCVOcupacion() {
		return CVOcupacion;
	}

	public void setCVOcupacion(String CVOcupacion) {
		this.CVOcupacion = CVOcupacion;
	}

	public String getCVDomicilio() {
		return CVDomicilio;
	}

	public void setCVDomicilio(String CVDomicilio) {
		this.CVDomicilio = CVDomicilio;
	}

	public String getCVLugarResidencia() {
		return CVLugarResidencia;
	}

	public void setCVLugarResidencia(String CVLugarResidencia) {
		this.CVLugarResidencia = CVLugarResidencia;
	}

	public String getCVCondicionMigratoria() {
		return CVCondicionMigratoria;
	}

	public void setCVCondicionMigratoria(String CVCondicionMigratoria) {
		this.CVCondicionMigratoria = CVCondicionMigratoria;
	}

	public String getCVEtnia() {
		return CVEtnia;
	}

	public void setCVEtnia(String CVEtnia) {
		this.CVEtnia = CVEtnia;
	}

	public String getCVSituacionJuridica() {
		return CVSituacionJuridica;
	}

	public void setCVSituacionJuridica(String CVSituacionJuridica) {
		this.CVSituacionJuridica = CVSituacionJuridica;
	}

	public String getCVEstadoConyugal() {
		return CVEstadoConyugal;
	}

	public void setCVEstadoConyugal(String CVEstadoConyugal) {
		this.CVEstadoConyugal = CVEstadoConyugal;
	}

	public String getCVPermisoPortacionArmas() {
		return CVPermisoPortacionArmas;
	}

	public void setCVPermisoPortacionArmas(String CVPermisoPortacionArmas) {
		this.CVPermisoPortacionArmas = CVPermisoPortacionArmas;
	}

	public String getCVPertenenciaFuerzaSeguridad() {
		return CVPertenenciaFuerzaSeguridad;
	}

	public void setCVPertenenciaFuerzaSeguridad(String CVPertenenciaFuerzaSeguridad) {
		this.CVPertenenciaFuerzaSeguridad = CVPertenenciaFuerzaSeguridad;
	}

	public String getCVAntecedentes() {
		return CVAntecedentes;
	}

	public char getCVSexo() {
		return CVSexo;
	}

	public void setCVSexo(char cVSexo) {
		CVSexo = cVSexo;
	}

	public void setCVAntecedentes(String CVAntecedentes) {
		this.CVAntecedentes = CVAntecedentes;
	}

	public String getCVSuicidio() {
		return CVSuicidio;
	}

	public void setCVSuicidio(String CVSuicidio) {
		this.CVSuicidio = CVSuicidio;
	}

	public String getCVGenerador() {
		return CVGenerador;
	}

	public void setCVGenerador(String CVGenerador) {
		this.CVGenerador = CVGenerador;
	}

	public String getCVDni() {
		return CVDni;
	}

	public void setCVDni(String cVDni) {
		CVDni = cVDni;
	}

	public String getCVNombre() {
		return CVNombre;
	}

	public void setCVNombre(String cVNombre) {
		CVNombre = cVNombre;
	}

	public String getCVGenero() {
		return CVGenero;
	}

	public void setCVGenero(String cVGenero) {
		CVGenero = cVGenero;
	}

	public String getCVOrientacionSexual() {
		return CVOrientacionSexual;
	}

	public void setCVOrientacionSexual(String cVOrientacionSexual) {
		CVOrientacionSexual = cVOrientacionSexual;
	}

	public String getCIEdad() {
		return CIEdad;
	}

	public void setCIEdad(String cIEdad) {
		CIEdad = cIEdad;
	}

	public String getCVLugarNacimiento() {
		return CVLugarNacimiento;
	}

	public void setCVLugarNacimiento(String cVLugarNacimiento) {
		CVLugarNacimiento = cVLugarNacimiento;
	}

	// pais
	public Integer getCodigoPais() {
		return this.codigoPais;
	}

	public void setCodigoPais(int codigoPais) {
		this.codigoPais = codigoPais;
	}
}
