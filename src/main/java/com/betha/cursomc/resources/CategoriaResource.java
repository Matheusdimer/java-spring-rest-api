package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @Autowired
    CategoriaService service;

    @GetMapping
    public List<Categoria> listAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> listOne(@PathVariable Integer id) {
        Optional<Categoria> categoria = service.getCategoria(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Categoria addCategoria(@RequestBody Categoria categoria) {
        return service.saveCategoria(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        categoria = service.editCategoria(id, categoria);

        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> removeCategoria(@PathVariable Integer id) {
        Categoria categoria = service.deleteCategoria(id);

        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
