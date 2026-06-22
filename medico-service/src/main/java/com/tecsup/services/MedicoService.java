package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Consultorio;
import com.tecsup.model.Especialidad;
import com.tecsup.model.Medico;
import com.tecsup.repository.ConsultorioRepository;
import com.tecsup.repository.EspecialidadRepository;
import com.tecsup.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public List<Medico> listar() {
        return repository.findAll();
    }

    public Medico guardar(Medico medico, Long idEspecialidad, Long idConsultorio) {
        medico.setEspecialidad(obtenerEspecialidad(idEspecialidad));
        medico.setConsultorio(obtenerConsultorio(idConsultorio));
        return repository.save(medico);
    }

    public Medico obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado con id: " + id));
    }

    public Medico actualizar(Long id, Medico medico, Long idEspecialidad, Long idConsultorio) {
        Medico existente = obtener(id);
        existente.setCmp(medico.getCmp());
        existente.setNombres(medico.getNombres());
        existente.setApellidos(medico.getApellidos());
        existente.setTelefono(medico.getTelefono());
        existente.setCorreo(medico.getCorreo());
        existente.setEspecialidad(obtenerEspecialidad(idEspecialidad));
        existente.setConsultorio(obtenerConsultorio(idConsultorio));
        existente.setEstado(medico.getEstado());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Especialidad obtenerEspecialidad(Long idEspecialidad) {
        return especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + idEspecialidad));
    }

    private Consultorio obtenerConsultorio(Long idConsultorio) {
        return consultorioRepository.findById(idConsultorio)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + idConsultorio));
    }
}
