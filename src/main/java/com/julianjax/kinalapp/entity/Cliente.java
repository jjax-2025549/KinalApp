package com.julianjax.kinalapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @NotBlank(message = "El DPI no puede estar vacío")
    @Size(min = 13, max = 13, message = "El DPI debe tener exactamente 13 dígitos")
    @Pattern(regexp = "\\d{13}", message = "El DPI solo debe contener números")
    @Column(name = "dpi_cliente")
    private String dpiCliente;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @NotBlank(message = "El apellido del cliente es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(name = "apellido_cliente")
    private String apellidoCliente;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(name = "direccion")
    private String direccion;

    @Column(name = "estado")
    private int estado;

    public Cliente() {}

    public Cliente(String dpiCliente, String nombreCliente, String apellidoCliente,
                   String direccion, int estado) {
        this.dpiCliente = dpiCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccion = direccion;
        this.estado = estado;
    }

    public String getDpiCliente() { return dpiCliente; }
    public void setDpiCliente(String dpiCliente) { this.dpiCliente = dpiCliente; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getApellidoCliente() { return apellidoCliente; }
    public void setApellidoCliente(String apellidoCliente) { this.apellidoCliente = apellidoCliente; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
