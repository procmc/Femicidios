package com.if7100.entity;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ta_lugar")

public class Lugar {

    /**
     * Esta es la clase para el JPA Entity de la tabla femicidios.TA_Usuarios
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CI_Codigo;

    @Column(name = "CI_Hecho", nullable = false)
    private int CIHecho;

    @Column(name = "CV_Descripcion", nullable = false)
    private String CV_Descripcion;

    @Column(name = "CI_Tipo_Lugar", nullable = false)
    private int CITipoLugar;

    @Column(name = "CV_Direccion", nullable = false)
    private String CV_Direccion;

    @Column(name = "CV_Ciudad", nullable = false)
    private String CV_Ciudad;

    @Column(name = "CI_Codigo_Postal", nullable = true)
    private int CI_Codigo_Postal;
    @Transient
    private String Titulo;

    @Column(name = "CV_Provincia", nullable = false)
    private String CVProvincia;

    @Column(name = "CV_Canton", nullable = false)
    private String CVCanton;

    @Column(name = "CV_Distrito", nullable = false)
    private String CVDistrito;

    public Lugar() {
    }

    public Lugar(int CI_Hecho, String CV_Descripcion, int CITipoLugar, String CV_Direccion, String CV_Ciudad, int CI_Codigo_Postal, String CVProvincia, String CVCanton,
            String CVDistrito) {
        super();
        this.CIHecho = CI_Hecho;
        this.CV_Descripcion = CV_Descripcion;
        this.CITipoLugar = CITipoLugar;
        this.CV_Direccion = CV_Direccion;
        this.CV_Ciudad = CV_Ciudad;
        this.CI_Codigo_Postal = CI_Codigo_Postal;
        this.CVProvincia = CVProvincia;
        this.CVCanton = CVCanton;
        this.CVDistrito = CVDistrito;
    }

    public Lugar(int CI_Hecho, String CV_Descripcion, int CITipoLugar, String CV_Direccion, String CV_Ciudad, int CI_Codigo_Postal, String Titulo) {
        super();
        this.CIHecho = CI_Hecho;
        this.CV_Descripcion = CV_Descripcion;
        this.CITipoLugar = CITipoLugar;
        this.CV_Direccion = CV_Direccion;
        this.CV_Ciudad = CV_Ciudad;
        this.Titulo = Titulo;
        this.CI_Codigo_Postal = CI_Codigo_Postal;
    }

    public int getCI_Codigo() {
        return CI_Codigo;
    }

    public void setCI_Codigo(int cI_Codigo) {
        CI_Codigo = cI_Codigo;
    }

    public int getCIHecho() {
        return CIHecho;
    }

    public void setCIHecho(int cI_Hecho) {
        CIHecho = cI_Hecho;
    }

    public String getCV_Descripcion() {
        return CV_Descripcion;
    }

    public void setCV_Descripcion(String cV_Descripcion) {
        CV_Descripcion = cV_Descripcion;
    }

    public int getCITipoLugar() {
        return CITipoLugar;
    }

    public void setCITipoLugar(int cITipoLugar) {
        CITipoLugar = cITipoLugar;
    }

    public String getCV_Direccion() {
        return CV_Direccion;
    }

    public void setCV_Direccion(String cV_Direccion) {
        CV_Direccion = cV_Direccion;
    }

    public String getCV_Ciudad() {
        return CV_Ciudad;
    }

    public void setCV_Ciudad(String cV_Ciudad) {
        CV_Ciudad = cV_Ciudad;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public int getCI_Codigo_Postal() {
        return CI_Codigo_Postal;
    }

    public void setCI_Codigo_Postal(int cI_Codigo_Postal) {
        CI_Codigo_Postal = cI_Codigo_Postal;
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
}
