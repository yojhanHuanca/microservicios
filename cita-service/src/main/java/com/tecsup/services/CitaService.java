package com.tecsup.services;

import com.tecsup.client.MedicoClientDTO;
import com.tecsup.client.PacienteClientDTO;
import com.tecsup.dto.CitaDTO;
import com.tecsup.dto.CitaDetalleDTO;
import com.tecsup.exception.BusinessException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Cita;
import com.tecsup.model.EstadoCita;
import com.tecsup.repository.CitaRepository;
import com.tecsup.repository.EstadoCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository repository;

    @Autowired
    private EstadoCitaRepository estadoRepository;

    private final RestTemplate restTemplate;

    public CitaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Cita> listar() {
        return repository.findAll();
    }

    public Cita guardar(CitaDTO dto) {
        validarReglasCita(null, dto);
        Cita cita = toEntity(dto);
        return repository.save(cita);
    }

    public Cita obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
    }

    public Cita actualizar(Long id, CitaDTO dto) {
        Cita existente = obtener(id);
        validarReglasCita(id, dto);
        existente.setFecha(dto.getFecha());
        existente.setHora(dto.getHora());
        existente.setIdPaciente(dto.getIdPaciente());
        existente.setIdMedico(dto.getIdMedico());
        existente.setMotivo(dto.getMotivo());
        existente.setEstado(obtenerEstado(dto.getIdEstado()));
        return repository.save(existente);
    }

    public Cita actualizarEstado(Long id, Long idEstado) {
        Cita cita = obtener(id);
        cita.setEstado(obtenerEstado(idEstado));
        return repository.save(cita);
    }

    public Cita cancelar(Long id) {
        Cita cita = obtener(id);
        cita.setEstado(obtenerEstadoPorDescripcion("CANCELADA"));
        return repository.save(cita);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    public CitaDetalleDTO obtenerDetalle(Long id) {
        Cita cita = obtener(id);
        PacienteClientDTO paciente = obtenerPaciente(cita.getIdPaciente());
        MedicoClientDTO medico = obtenerMedico(cita.getIdMedico());

        CitaDetalleDTO detalle = new CitaDetalleDTO();
        detalle.setIdCita(cita.getIdCita());
        detalle.setFecha(cita.getFecha());
        detalle.setHora(cita.getHora());
        detalle.setEstado(cita.getEstado().getDescripcion());
        detalle.setPaciente(new CitaDetalleDTO.PacienteResumen(
                paciente.getNombres() + " " + paciente.getApellidos(),
                paciente.getDni()));
        detalle.setMedico(new CitaDetalleDTO.MedicoResumen(
                medico.getNombres() + " " + medico.getApellidos(),
                medico.getEspecialidad() != null ? medico.getEspecialidad().getNombre() : null));
        detalle.setConsultorio(new CitaDetalleDTO.ConsultorioResumen(
                medico.getConsultorio() != null ? medico.getConsultorio().getNumero() : null,
                medico.getConsultorio() != null ? medico.getConsultorio().getPiso() : null));
        return detalle;
    }

    private Cita toEntity(CitaDTO dto) {
        Cita cita = new Cita();
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setIdPaciente(dto.getIdPaciente());
        cita.setIdMedico(dto.getIdMedico());
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(obtenerEstado(dto.getIdEstado()));
        return cita;
    }

    private void validarReglasCita(Long idCita, CitaDTO dto) {
        obtenerPaciente(dto.getIdPaciente());
        obtenerMedico(dto.getIdMedico());
        validarDisponibilidadMedico(dto.getIdMedico(), dto.getFecha(), dto.getHora());

        boolean existeCruce = idCita == null
                ? repository.existsByIdMedicoAndFechaAndHoraAndEstado_DescripcionNot(dto.getIdMedico(), dto.getFecha(), dto.getHora(), "CANCELADA")
                : repository.existsByIdCitaNotAndIdMedicoAndFechaAndHoraAndEstado_DescripcionNot(idCita, dto.getIdMedico(), dto.getFecha(), dto.getHora(), "CANCELADA");
        if (existeCruce) {
            throw new BusinessException("El medico ya tiene una cita registrada en la misma fecha y hora");
        }
    }

    private void validarDisponibilidadMedico(Long idMedico, LocalDate fecha, LocalTime hora) {
        Boolean disponible = restTemplate.getForObject(
                "http://medico-service/api/horarios-medicos/disponibilidad?idMedico={idMedico}&fecha={fecha}&hora={hora}",
                Boolean.class,
                idMedico, fecha, hora);
        if (!Boolean.TRUE.equals(disponible)) {
            throw new BusinessException("El medico no tiene horario disponible para la fecha y hora indicada");
        }
    }

    private PacienteClientDTO obtenerPaciente(Long idPaciente) {
        try {
            return restTemplate.getForObject(
                    "http://paciente-service/api/pacientes/{id}",
                    PacienteClientDTO.class,
                    idPaciente);
        } catch (RestClientException ex) {
            throw new ResourceNotFoundException("Paciente no encontrado con id: " + idPaciente);
        }
    }

    private MedicoClientDTO obtenerMedico(Long idMedico) {
        try {
            return restTemplate.getForObject(
                    "http://medico-service/api/medicos/{id}",
                    MedicoClientDTO.class,
                    idMedico);
        } catch (RestClientException ex) {
            throw new ResourceNotFoundException("Medico no encontrado con id: " + idMedico);
        }
    }

    private EstadoCita obtenerEstado(Long idEstado) {
        if (idEstado == null) {
            return obtenerEstadoPorDescripcion("PROGRAMADA");
        }
        return estadoRepository.findById(idEstado)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado con id: " + idEstado));
    }

    private EstadoCita obtenerEstadoPorDescripcion(String descripcion) {
        return estadoRepository.findByDescripcionIgnoreCase(descripcion)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado: " + descripcion));
    }
}
