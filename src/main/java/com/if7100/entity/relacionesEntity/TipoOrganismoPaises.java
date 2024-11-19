package com.if7100.entity.relacionesEntity;

import com.if7100.entity.Paises;
import com.if7100.entity.TipoOrganismo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_tipoorganismopais")
public class TipoOrganismoPaises {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipoorganismopais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_tipoorganismo")
    private TipoOrganismo tipoOrganismo;

    // Getters y Setters

    public Integer getId_tipoorganismopais() {
        return id_tipoorganismopais;
    }

    public void setId_tipoorganismopais(Integer id_tipoorganismopais) {
        this.id_tipoorganismopais = id_tipoorganismopais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public TipoOrganismo getTipoOrganismo() {
        return tipoOrganismo;
    }

    public void setTipoOrganismo(TipoOrganismo tipoOrganismo) {
        this.tipoOrganismo = tipoOrganismo;
    }
}
