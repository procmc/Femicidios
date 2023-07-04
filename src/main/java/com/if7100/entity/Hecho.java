package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TA_Hechos")
public class Hecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CI_Pais", nullable = false)
    private Integer CIPais;
    

    @Column(name = "CI_Tipo_Victima", nullable = false)
    private Integer CITipoVictima;

    @Column(name = "CI_Tipo_Relacion", nullable = false)
    private Integer CITipoRelacion;

    @Column(name = "CI_Modalidad", nullable = false)
    private Integer CIModalidad;

    @Column(name = "CI_Id_Victima", nullable = false)
    private Integer CIIdVictima;

    @Column(name = "CI_Id_Proceso", nullable = false)
    private Integer CIIdProceso;
    
    @Column(name = "CV_Provincia", nullable = false)
    private String CVProvincia;
    
    @Column(name = "CV_Canton", nullable = false)
    private String CVCanton;
    
    @Column(name = "CV_Distrito", nullable = false)
    private String CVDistrito;
    
    @Column(name = "CV_Agresion_Sexual", nullable = false)
    private String CVAgresionSexual;

    @Column(name = "CV_Denuncia_Previa", nullable = false)
    private String CVDenunciaPrevia;

    @Column(name = "CI_Id_Generador", nullable = false)
    private Integer CIIdGenerador;

    @Column(name = "CD_Fecha", nullable = false)
    private String CDFecha;

    @Column(name = "CV_Detalles", nullable = false)
    private String CVDetalles;

    public Hecho(){}

    public Hecho(Integer CI_Id, Integer CITipoVictima, Integer CITipoRelacion, Integer CIModalidad,
                 Integer CIIdVictima, Integer CIIdProceso, String CVAgresionSexual,
                 String CVDenunciaPrevia, Integer CIIdGenerador, Integer CIPais, String CVProvincia,String CVCanton,
                 String CVDistrito, String CDFecha, String CVDetalles) {
        this.CI_Id = CI_Id;
        this.CIPais = CIPais;
        this.CVProvincia= CVProvincia;
        this.CVCanton= CVCanton;
        this.CVDistrito= CVDistrito;
        this.CITipoVictima = CITipoVictima;
        this.CITipoRelacion = CITipoRelacion;
        this.CIModalidad = CIModalidad;
        this.CIIdVictima = CIIdVictima;
        this.CIIdProceso = CIIdProceso;
        this.CVAgresionSexual = CVAgresionSexual;
        this.CVDenunciaPrevia = CVDenunciaPrevia;
        this.CIIdGenerador = CIIdGenerador;
        this.CDFecha = CDFecha;
        this.CVDetalles = CVDetalles;
    }

    public String getCVDetalles() {
        return CVDetalles;
    }

    public void setCVDetalles(String CVDetalles) {
        this.CVDetalles = CVDetalles;
    }

    public String getCDFecha() {
        return CDFecha;
    }

    public void setCDFecha(String CDFecha) {
        this.CDFecha = CDFecha;
    }

    public String getCVAgresionSexual() {
        return CVAgresionSexual;
    }

    public void setCVAgresionSexual(String CVAgresionSexual) {
        this.CVAgresionSexual = CVAgresionSexual;
    }

    public String getCVDenunciaPrevia() {
        return CVDenunciaPrevia;
    }

    public void setCVDenunciaPrevia(String CVDenunciaPrevia) {
        this.CVDenunciaPrevia = CVDenunciaPrevia;
    }

    public Integer getCIIdGenerador() {
        return CIIdGenerador;
    }

    public void setCIIdGenerador(Integer CIIdGenerador) {
        this.CIIdGenerador = CIIdGenerador;
    }

    public Integer getCIIdVictima() {
        return CIIdVictima;
    }

    public void setCIIdVictima(Integer CIIdVictima) {
        this.CIIdVictima = CIIdVictima;
    }

    public Integer getCIIdProceso() {
        return CIIdProceso;
    }

    public void setCIIdProceso(Integer CIIdProceso) {
        this.CIIdProceso = CIIdProceso;
    }

    public Integer getCI_Id() {
        return CI_Id;
    }

    public void setCI_Id(Integer CI_Id) {
        this.CI_Id = CI_Id;
    }

    public Integer getCIPais() {
        return CIPais;
    }

    public void setCIPais(Integer CVPais) {
        this.CIPais = CVPais;
    }
    
    

    public String getCVProvincia() {
		return CVProvincia;
	}

	public void setCVProvincia(String cVProvincia) {
		CVProvincia = cVProvincia;
	}

	public String getCVCanton() {
		return CVCanton;
	}

	public void setCVCanton(String cVCanton) {
		CVCanton = cVCanton;
	}

	public String getCVDistrito() {
		return CVDistrito;
	}

	public void setCVDistrito(String cVDistrito) {
		CVDistrito = cVDistrito;
	}

	public Integer getCITipoVictima() {
        return CITipoVictima;
    }

    public void setCITipoVictima(Integer CITipoVictima) {
        this.CITipoVictima = CITipoVictima;
    }

    public Integer getCITipoRelacion() {
        return CITipoRelacion;
    }

    public void setCITipoRelacion(Integer CITipoRelacion) {
        this.CITipoRelacion = CITipoRelacion;
    }

    public Integer getCIModalidad() {
        return CIModalidad;
    }

    public void setCIModalidad(Integer CIModalidad) {
        this.CIModalidad = CIModalidad;
    }
}
