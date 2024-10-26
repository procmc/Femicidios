package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TA_Hechos")
public class Hecho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CI_Tipo_Victima", nullable = false)
    private Integer CITipoVictima;

    @Column(name = "CI_Tipo_Relacion", nullable = false)
    private Integer CITipoRelacion;

    @Column(name = "CI_Modalidad", nullable = false)
    private Integer CIModalidad;

    // @Column(name = "CI_Id_Victima", nullable = false)
    // private Integer CIIdVictima;

    // @Column(name = "CI_Id_Proceso", nullable = false)
    // private Integer CIIdProceso;

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

    // hecho con victima
    @ManyToOne
    @JoinColumn(name = "CI_Id_Victima", referencedColumnName = "CI_Id", nullable = false)
    private Victima victima;

    // hecho con proceso judicial
    @ManyToOne
    @JoinColumn(name = "CI_Id_Proceso", referencedColumnName = "CI_Id", nullable = false)
    private ProcesoJudicial procesoJudicial;

    @Column(name = "ci_codigo_pais", nullable = false)
    private Integer codigoPais;

    public Hecho() {
        // TODO Auto-generated constructor stub
    }

    public Hecho(Integer CITipoVictima, Integer CITipoRelacion, Integer CIModalidad,
            ProcesoJudicial procesoJudicial, Victima victima, String CVAgresionSexual,
            String CVDenunciaPrevia, Integer CIIdGenerador, String CDFecha, String CVDetalles, int codigoPais) {

        super();
        this.CITipoVictima = CITipoVictima;
        this.CITipoRelacion = CITipoRelacion;
        this.CIModalidad = CIModalidad;
        this.procesoJudicial = procesoJudicial;
        this.victima = victima;
        // this.CIIdVictima = CIIdVictima;
        // this.CIIdProceso = CIIdProceso;
        this.CVAgresionSexual = CVAgresionSexual;
        this.CVDenunciaPrevia = CVDenunciaPrevia;
        this.CIIdGenerador = CIIdGenerador;
        this.CDFecha = CDFecha;
        this.CVDetalles = CVDetalles;
        this.codigoPais = codigoPais;
    }

    // get y set de relacion de hecho con victima
    public Victima getVictima() {
        return victima;
    }

    public void setVictima(Victima victima) {
        this.victima = victima;
    }

    // get y set de relacion de hecho con proceso judicial
    public ProcesoJudicial getProcesoJudicial() {
        return procesoJudicial;
    }

    public void setProcesoJudicial(ProcesoJudicial procesoJudicial) {
        this.procesoJudicial = procesoJudicial;
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

    /*
     * public Integer getCIIdVictima() {
     * return CIIdVictima;
     * }
     * 
     * public void setCIIdVictima(Integer CIIdVictima) {
     * this.CIIdVictima = CIIdVictima;
     * }
     * 
     * 
     * public Integer getCIIdProceso() {
     * return CIIdProceso;
     * }
     * 
     * public void setCIIdProceso(Integer CIIdProceso) {
     * this.CIIdProceso = CIIdProceso;
     * }
     */

    public Integer getCI_Id() {
        return CI_Id;
    }

    public void setCI_Id(Integer CI_Id) {
        this.CI_Id = CI_Id;
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

    // pais
    public Integer getCodigoPais() {
        return this.codigoPais;
    }

    public void setCodigoPais(int codigoPais) {
        this.codigoPais = codigoPais;
    }

}
