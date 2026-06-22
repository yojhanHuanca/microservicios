package com.tecsup.dto;

import jakarta.validation.constraints.NotBlank;

public class EstadoCitaDTO {

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
