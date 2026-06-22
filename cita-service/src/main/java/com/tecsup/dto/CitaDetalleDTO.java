package com.tecsup.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDetalleDTO {

    private Long idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private PacienteResumen paciente;
    private MedicoResumen medico;
    private ConsultorioResumen consultorio;

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PacienteResumen getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteResumen paciente) {
        this.paciente = paciente;
    }

    public MedicoResumen getMedico() {
        return medico;
    }

    public void setMedico(MedicoResumen medico) {
        this.medico = medico;
    }

    public ConsultorioResumen getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(ConsultorioResumen consultorio) {
        this.consultorio = consultorio;
    }

    public static class PacienteResumen {
        private String nombre;
        private String dni;

        public PacienteResumen(String nombre, String dni) {
            this.nombre = nombre;
            this.dni = dni;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDni() {
            return dni;
        }
    }

    public static class MedicoResumen {
        private String nombre;
        private String especialidad;

        public MedicoResumen(String nombre, String especialidad) {
            this.nombre = nombre;
            this.especialidad = especialidad;
        }

        public String getNombre() {
            return nombre;
        }

        public String getEspecialidad() {
            return especialidad;
        }
    }

    public static class ConsultorioResumen {
        private String numero;
        private Integer piso;

        public ConsultorioResumen(String numero, Integer piso) {
            this.numero = numero;
            this.piso = piso;
        }

        public String getNumero() {
            return numero;
        }

        public Integer getPiso() {
            return piso;
        }
    }
}
