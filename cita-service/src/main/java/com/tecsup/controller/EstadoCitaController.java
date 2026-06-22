package com.tecsup.controller;

import com.tecsup.dto.EstadoCitaDTO;
import com.tecsup.model.EstadoCita;
import com.tecsup.services.EstadoCitaService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estados-cita")
public class EstadoCitaController {

    @Autowired
    private EstadoCitaService service;

    @GetMapping
    public ResponseEntity<List<EstadoCita>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoCita> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<EstadoCita> guardar(@Valid @RequestBody EstadoCitaDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoCita> actualizar(@PathVariable Long id, @Valid @RequestBody EstadoCitaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Estado de cita eliminado correctamente");
    }

    private EstadoCita toEntity(EstadoCitaDTO dto) {
        EstadoCita estado = new EstadoCita();
        estado.setDescripcion(dto.getDescripcion());
        return estado;
    }
}
