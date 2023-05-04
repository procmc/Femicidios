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
@Table(name= "ta_lugar")


public class Lugar {

    /**
     *  Esta es la clase para el JPA Entity de la tabla femicidios.TA_Usuarios
     */
    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private int CI_Codigo;

    @Column (name="CI_Hecho", nullable=false)
    private int CIHecho;

    @Column (name="CV_Descripcion", nullable=false)
    private String CV_Descripcion;

    @Column (name="CI_Tipo_Lugar", nullable=false)
    private int CI_Tipo_Lugar;

    @Column (name="CV_Direccion", nullable=false)
    private String CV_Direccion;

    @Column (name="CV_Ciudad", nullable=false)
    private String CV_Ciudad;

    @Column (name="CI_Pais", nullable=false)
    private int CI_Pais;

    @Transient
    private String Titulo;

    public Lugar() {
    }

    public Lugar(int CI_Hecho, String CV_Descripcion, int CI_Tipo_Lugar, String CV_Direccion, String CV_Ciudad, int CI_Pais, String Titulo) {
        super();
        this.CIHecho= CI_Hecho;
        this.CV_Descripcion= CV_Descripcion;
        this.CI_Tipo_Lugar= CI_Tipo_Lugar;
        this.CV_Direccion= CV_Direccion;
        this.CV_Ciudad= CV_Ciudad;
        this.CI_Pais= CI_Pais;
        this.Titulo=Titulo;
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

    public int getCI_Tipo_Lugar() {
        return CI_Tipo_Lugar;
    }

    public void setCI_Tipo_Lugar(int cI_Tipo_Lugar) {
        CI_Tipo_Lugar = cI_Tipo_Lugar;
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

    public int getCI_Pais() {
        return CI_Pais;
    }

    public void setCI_Pais(int cI_Pais) {
        CI_Pais = cI_Pais;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }




}
