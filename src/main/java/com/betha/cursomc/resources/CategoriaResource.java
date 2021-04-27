package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.dto.CategoriaDTO;
import com.betha.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @Autowired
    CategoriaService service;

    @GetMapping
    public List<CategoriaDTO> listAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Categoria listOne(@PathVariable Integer id) {
        return service.getCategoria(id);
    }

    @PostMapping
    public ResponseEntity<Categoria> addCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaSalva = service.saveCategoria(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(categoriaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @PutMapping("/{id}")
    public Categoria editCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return service.editCategoria(id, categoria);
    }

    @DeleteMapping("/{id}")
    public Categoria removeCategoria(@PathVariable Integer id) {
        return service.deleteCategoria(id);
    }
}
