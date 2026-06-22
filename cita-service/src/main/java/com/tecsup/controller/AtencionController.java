package com.tecsup.controller;

import com.tecsup.dto.AtencionDTO;
import com.tecsup.model.Atencion;
import com.tecsup.services.AtencionService;
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
@RequestMapping("/api/atenciones")
public class AtencionController {

    @Autowired
    private AtencionService service;

    @GetMapping
    public ResponseEntity<List<Atencion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atencion> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Atencion> guardar(@Valid @RequestBody AtencionDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(toEntity(dto), dto.getIdCita()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atencion> actualizar(@PathVariable Long id, @Valid @RequestBody AtencionDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, toEntity(dto), dto.getIdCita()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Atencion eliminada correctamente");
    }

    private Atencion toEntity(AtencionDTO dto) {
        Atencion atencion = new Atencion();
        atencion.setDiagnostico(dto.getDiagnostico());
        atencion.setTratamiento(dto.getTratamiento());
        atencion.setObservaciones(dto.getObservaciones());
        return atencion;
    }
}
