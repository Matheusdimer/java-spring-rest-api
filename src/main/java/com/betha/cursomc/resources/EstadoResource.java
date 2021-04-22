package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Estado;
import com.betha.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public List<Estado> getEstados() {
        return estadoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> getEstado(@PathVariable Integer id) {
        Estado estado = estadoService.getOne(id);

        return ResponseEntity.ok(estado);
    }

    @PostMapping
    public ResponseEntity<Estado> addEstado(@RequestBody Estado estado) {
        estado = estadoService.save(estado);

        return ResponseEntity.created(URI.create("/produtos/" + estado.getId())).body(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> editEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        estado = estadoService.edit(id, estado);

        return ResponseEntity.ok(estado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estado> removeEstado(@PathVariable Integer id) {
        Estado estado = estadoService.delete(id);

        return ResponseEntity.ok(estado);
    }
}
