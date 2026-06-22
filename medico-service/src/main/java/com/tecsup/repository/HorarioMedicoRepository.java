package com.tecsup.repository;

import com.tecsup.model.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {

    List<HorarioMedico> findByMedico_IdMedico(Long idMedico);
}
