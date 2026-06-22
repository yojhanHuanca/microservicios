package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.EstadoCita;
import com.tecsup.repository.EstadoCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoCitaService {

    @Autowired
    private EstadoCitaRepository repository;

    public List<EstadoCita> listar() {
        return repository.findAll();
    }

    public EstadoCita guardar(EstadoCita estado) {
        estado.setDescripcion(estado.getDescripcion().toUpperCase());
        return repository.save(estado);
    }

    public EstadoCita obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado con id: " + id));
    }

    public EstadoCita actualizar(Long id, EstadoCita estado) {
        EstadoCita existente = obtener(id);
        existente.setDescripcion(estado.getDescripcion().toUpperCase());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }
}
