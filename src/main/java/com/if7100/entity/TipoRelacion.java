package com.if7100.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TA_Tiporelacion")
public class TipoRelacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Codigo;

    @Column(name = "CV_Titulo", nullable = false)
    private String CVTitulo;

    @Column(name = "CV_Descripcion", nullable = false)
    private String CVDescripcion;

    public TipoRelacion() {
    }

//    public TipoRelacion(Integer CI_Codigo, String CVTitulo, String CVDescripcion) {
//        this.CI_Codigo = CI_Codigo;
//        this.CVTitulo = CVTitulo;
//        this.CVDescripcion = CVDescripcion;
//    }

    public TipoRelacion(String CVTitulo, String CVDescripcion) {
        this.CVTitulo = CVTitulo;
        this.CVDescripcion = CVDescripcion;
    }

    public Integer getCI_Codigo() {
        return CI_Codigo;
    }

    public void setCI_Codigo(Integer CI_Codigo) {
        this.CI_Codigo = CI_Codigo;
    }

    public String getCVTitulo() {
        return CVTitulo;
    }

    public void setCVTitulo(String CVTitulo) {
        this.CVTitulo = CVTitulo;
    }

    public String getCVDescripcion() {
        return CVDescripcion;
    }

    public void setCVDescripcion(String CVDescripcion) {
        this.CVDescripcion = CVDescripcion;
    }
}
