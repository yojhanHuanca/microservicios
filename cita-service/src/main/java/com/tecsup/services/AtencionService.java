package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Atencion;
import com.tecsup.model.Cita;
import com.tecsup.repository.AtencionRepository;
import com.tecsup.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository repository;

    @Autowired
    private CitaRepository citaRepository;

    public List<Atencion> listar() {
        return repository.findAll();
    }

    public Atencion guardar(Atencion atencion, Long idCita) {
        atencion.setCita(obtenerCita(idCita));
        return repository.save(atencion);
    }

    public Atencion obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con id: " + id));
    }

    public Atencion actualizar(Long id, Atencion atencion, Long idCita) {
        Atencion existente = obtener(id);
        existente.setCita(obtenerCita(idCita));
        existente.setDiagnostico(atencion.getDiagnostico());
        existente.setTratamiento(atencion.getTratamiento());
        existente.setObservaciones(atencion.getObservaciones());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Cita obtenerCita(Long idCita) {
        return citaRepository.findById(idCita)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + idCita));
    }
}
