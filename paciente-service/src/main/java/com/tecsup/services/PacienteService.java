package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Paciente;
import com.tecsup.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Paciente guardar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Paciente obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
    }

    public Paciente actualizar(Long id, Paciente paciente) {
        Paciente existente = obtener(id);
        existente.setDni(paciente.getDni());
        existente.setNombres(paciente.getNombres());
        existente.setApellidos(paciente.getApellidos());
        existente.setFechaNacimiento(paciente.getFechaNacimiento());
        existente.setSexo(paciente.getSexo());
        existente.setTelefono(paciente.getTelefono());
        existente.setDireccion(paciente.getDireccion());
        existente.setCorreo(paciente.getCorreo());
        existente.setEstado(paciente.getEstado());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }
}
