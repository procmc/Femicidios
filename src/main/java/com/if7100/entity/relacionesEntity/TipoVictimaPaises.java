package com.if7100.entity.relacionesEntity;

import com.if7100.entity.Paises;
import com.if7100.entity.TipoVictima;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_tipovictimapais")
public class TipoVictimaPaises {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipovictimapais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_tipovictima")
    private TipoVictima tipoVictima;

    // Getters y Setters

    public Integer getId_tipovictimapais() {
        return id_tipovictimapais;
    }

    public void setId_tipovictimapais(Integer id_tipovictimapais) {
        this.id_tipovictimapais = id_tipovictimapais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public TipoVictima getTipoVictima() {
        return tipoVictima;
    }

    public void setTipoVictima(TipoVictima tipoVictima) {
        this.tipoVictima = tipoVictima;
    }
}
