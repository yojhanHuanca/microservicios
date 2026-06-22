package com.tecsup.controller;

import com.tecsup.dto.ConsultorioDTO;
import com.tecsup.model.Consultorio;
import com.tecsup.services.ConsultorioService;
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
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    @Autowired
    private ConsultorioService service;

    @GetMapping
    public ResponseEntity<List<Consultorio>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultorio> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Consultorio> guardar(@Valid @RequestBody ConsultorioDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> actualizar(@PathVariable Long id, @Valid @RequestBody ConsultorioDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Consultorio eliminado correctamente");
    }

    private Consultorio toEntity(ConsultorioDTO dto) {
        Consultorio consultorio = new Consultorio();
        consultorio.setNumero(dto.getNumero());
        consultorio.setPiso(dto.getPiso());
        consultorio.setUbicacion(dto.getUbicacion());
        return consultorio;
    }
}
