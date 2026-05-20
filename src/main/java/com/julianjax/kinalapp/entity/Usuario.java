package com.julianjax.kinalapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    private Long codigoUsuario;

    @NotBlank(message = "El username no puede estar vacío")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no tiene un formato válido")
    @Column(name = "email", unique = true)
    private String email;

    // ADMIN o USER, nada más
    @Column(name = "rol")
    private String rol;

    @Column(name = "estado")
    private int estado;

    public Usuario() {}

    public Usuario(Long codigoUsuario, String username, String password,
                   String email, String rol, int estado) {
        this.codigoUsuario = codigoUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
    }

    public Long getCodigoUsuario() { return codigoUsuario; }
    public void setCodigoUsuario(Long codigoUsuario) { this.codigoUsuario = codigoUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
