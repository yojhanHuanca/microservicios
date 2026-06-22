package com.tecsup.repository;

import com.tecsup.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Long> {

    Optional<EstadoCita> findByDescripcionIgnoreCase(String descripcion);
}
