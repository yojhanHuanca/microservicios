package com.tecsup.repository;

import com.tecsup.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByIdMedicoAndFechaAndHoraAndEstado_DescripcionNot(Long idMedico, LocalDate fecha, LocalTime hora, String estado);

    boolean existsByIdCitaNotAndIdMedicoAndFechaAndHoraAndEstado_DescripcionNot(Long idCita, Long idMedico, LocalDate fecha, LocalTime hora, String estado);
}
