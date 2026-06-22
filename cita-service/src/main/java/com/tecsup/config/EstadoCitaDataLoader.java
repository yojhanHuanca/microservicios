package com.tecsup.config;

import com.tecsup.model.EstadoCita;
import com.tecsup.repository.EstadoCitaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EstadoCitaDataLoader {

    @Bean
    public CommandLineRunner cargarEstadosCita(EstadoCitaRepository repository) {
        return args -> {
            crearSiNoExiste(repository, "PROGRAMADA");
            crearSiNoExiste(repository, "CONFIRMADA");
            crearSiNoExiste(repository, "ATENDIDA");
            crearSiNoExiste(repository, "CANCELADA");
        };
    }

    private void crearSiNoExiste(EstadoCitaRepository repository, String descripcion) {
        repository.findByDescripcionIgnoreCase(descripcion)
                .orElseGet(() -> {
                    EstadoCita estado = new EstadoCita();
                    estado.setDescripcion(descripcion);
                    return repository.save(estado);
                });
    }
}
