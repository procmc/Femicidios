package com.if7100.entity.relacionesEntity;

import com.if7100.entity.Paises;
import com.if7100.entity.TipoRelacion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_tiporelacionpais")
public class TipoRelacionPaises {
    
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tiporelacionpais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_tiporelacion")
    private TipoRelacion tipoRelacion;

    // Getters y Setters

    public Integer getId_tiporelacionpais() {
        return id_tiporelacionpais;
    }

    public void setId_tiporelacionpais(Integer id_tiporelacionpais) {
        this.id_tiporelacionpais = id_tiporelacionpais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public TipoRelacion getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(TipoRelacion tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

}
