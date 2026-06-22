package com.tecsup.controller;

import com.tecsup.dto.HorarioMedicoDTO;
import com.tecsup.model.HorarioMedico;
import com.tecsup.services.HorarioMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/horarios-medicos")
public class HorarioMedicoController {

    @Autowired
    private HorarioMedicoService service;

    @GetMapping
    public ResponseEntity<List<HorarioMedico>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioMedico> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> estaDisponible(@RequestParam Long idMedico,
                                                  @RequestParam LocalDate fecha,
                                                  @RequestParam LocalTime hora) {
        return ResponseEntity.ok(service.estaDisponible(idMedico, fecha, hora));
    }

    @PostMapping
    public ResponseEntity<HorarioMedico> guardar(@Valid @RequestBody HorarioMedicoDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(toEntity(dto), dto.getIdMedico()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioMedico> actualizar(@PathVariable Long id, @Valid @RequestBody HorarioMedicoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, toEntity(dto), dto.getIdMedico()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Horario eliminado correctamente");
    }

    private HorarioMedico toEntity(HorarioMedicoDTO dto) {
        HorarioMedico horario = new HorarioMedico();
        horario.setDiaSemana(dto.getDiaSemana());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());
        return horario;
    }
}
