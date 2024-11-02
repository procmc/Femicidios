package com.if7100.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ti_usuario_perfil")
public class UsuarioPerfil {

    @EmbeddedId
    private UsuarioPerfilId id;

    @ManyToOne
    @MapsId("CVCedulaUsuario") // Mapea usuarioId de UsuarioPerfilId a Usuario
    @JoinColumn(name = "CV_Cedula_Usuario", referencedColumnName = "CV_Cedula")
    private Usuario usuario;
    
    @ManyToOne
    @MapsId("CIIdPerfil") // Mapea perfilId de UsuarioPerfilId a Perfil
    @JoinColumn(name = "CI_Id_perfil", referencedColumnName = "CI_Id")
    private Perfil perfil;

    // Constructor, getters y setters
    public UsuarioPerfil() {}

    public UsuarioPerfil(Usuario usuario, Perfil perfil) {
        this.id = new UsuarioPerfilId(usuario.getCVCedula(), perfil.getCI_Id());
        this.usuario = usuario;
        this.perfil = perfil;
    }

    public UsuarioPerfilId getId() {
        return id;
    }

    public void setId(UsuarioPerfilId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    // Clase estática interna para la clave compuesta
    @Embeddable
    public static class UsuarioPerfilId implements Serializable {

        @Column(name = "CV_Cedula_Usuario")
        private String CVCedulaUsuario;

        @Column(name = "CI_Id_Perfil")
        private Integer CIIdPerfil;

        public UsuarioPerfilId() {}

        public UsuarioPerfilId(String CVCedulaUsuario, Integer CIIdPerfil) {
            this.CVCedulaUsuario = CVCedulaUsuario;
            this.CIIdPerfil = CIIdPerfil;
        }

        public String getCVCedulaUsuario() {
            return CVCedulaUsuario;
        }

        public void setCVCedulaUsuario(String CVCedulaUsuario) {
            this.CVCedulaUsuario = CVCedulaUsuario;
        }

        public Integer getCIIdPerfil() {
            return CIIdPerfil;
        }

        public void setCIIdPerfil(Integer CIIdPerfil) {
            this.CIIdPerfil = CIIdPerfil;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UsuarioPerfilId that = (UsuarioPerfilId) o;
            return Objects.equals(CIIdPerfil, that.CIIdPerfil) && Objects.equals(CIIdPerfil, that.CIIdPerfil);
        }

        @Override
        public int hashCode() {
            return Objects.hash(CIIdPerfil, CIIdPerfil);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPerfil that = (UsuarioPerfil) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
