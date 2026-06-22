package com.tecsup.services;

import com.tecsup.exception.BusinessException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.HorarioMedico;
import com.tecsup.model.Medico;
import com.tecsup.repository.HorarioMedicoRepository;
import com.tecsup.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class HorarioMedicoService {

    @Autowired
    private HorarioMedicoRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    public List<HorarioMedico> listar() {
        return repository.findAll();
    }

    public HorarioMedico guardar(HorarioMedico horario, Long idMedico) {
        validarHorario(horario);
        horario.setMedico(obtenerMedico(idMedico));
        return repository.save(horario);
    }

    public HorarioMedico obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + id));
    }

    public HorarioMedico actualizar(Long id, HorarioMedico horario, Long idMedico) {
        validarHorario(horario);
        HorarioMedico existente = obtener(id);
        existente.setMedico(obtenerMedico(idMedico));
        existente.setDiaSemana(horario.getDiaSemana());
        existente.setHoraInicio(horario.getHoraInicio());
        existente.setHoraFin(horario.getHoraFin());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    public boolean estaDisponible(Long idMedico, LocalDate fecha, LocalTime hora) {
        obtenerMedico(idMedico);
        String diaSolicitud = normalizarDia(fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "PE")));
        return repository.findByMedico_IdMedico(idMedico).stream()
                .filter(horario -> normalizarDia(horario.getDiaSemana()).equals(diaSolicitud))
                .anyMatch(horario -> !hora.isBefore(horario.getHoraInicio()) && hora.isBefore(horario.getHoraFin()));
    }

    private Medico obtenerMedico(Long idMedico) {
        return medicoRepository.findById(idMedico)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado con id: " + idMedico));
    }

    private void validarHorario(HorarioMedico horario) {
        if (horario.getHoraInicio() != null && horario.getHoraFin() != null
                && !horario.getHoraInicio().isBefore(horario.getHoraFin())) {
            throw new BusinessException("La hora de inicio debe ser menor que la hora de fin");
        }
    }

    private String normalizarDia(String diaSemana) {
        if (diaSemana == null) {
            return "";
        }
        return diaSemana.trim()
                .toUpperCase(Locale.ROOT)
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");
    }
}
