package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Consultorio;
import com.tecsup.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private ConsultorioRepository repository;

    public List<Consultorio> listar() {
        return repository.findAll();
    }

    public Consultorio guardar(Consultorio consultorio) {
        return repository.save(consultorio);
    }

    public Consultorio obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + id));
    }

    public Consultorio actualizar(Long id, Consultorio consultorio) {
        Consultorio existente = obtener(id);
        existente.setNumero(consultorio.getNumero());
        existente.setPiso(consultorio.getPiso());
        existente.setUbicacion(consultorio.getUbicacion());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }
}
