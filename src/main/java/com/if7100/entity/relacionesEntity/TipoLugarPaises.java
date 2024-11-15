package com.if7100.entity.relacionesEntity;

import com.if7100.entity.Paises;
import com.if7100.entity.TipoLugar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_tipolugarpais")
public class TipoLugarPaises {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipolugarpais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_tipolugar")
    private TipoLugar tipoLugar;

    // Getters y Setters

    public Integer getId_tipolugarpais() {
        return id_tipolugarpais;
    }

    public void setId_tipolugarpais(Integer id_tipolugarpais) {
        this.id_tipolugarpais = id_tipolugarpais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public TipoLugar getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(TipoLugar tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

}
