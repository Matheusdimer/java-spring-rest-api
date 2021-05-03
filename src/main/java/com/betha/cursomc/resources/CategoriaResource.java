package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.domain.dto.CategoriaDTO;
import com.betha.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<CategoriaDTO> listAll() {
        return service.getAll();
    }

    @GetMapping("/page")
    public Page<CategoriaDTO> findPage(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "lines", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "orderby", defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> categorias = service.findPerPages(page, linesPerPage, orderBy, direction);
        return categorias.map(CategoriaDTO::new);
    }

    @GetMapping("/{id}")
    public Categoria listOne(@PathVariable Integer id) {
        return service.getCategoria(id);
    }

    @PostMapping
    public ResponseEntity<Categoria> addCategoria(@Valid @RequestBody CategoriaDTO cat) {
        Categoria categoria = service.fromDTO(cat);
        Categoria categoriaSalva = service.saveCategoria(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(categoriaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @PutMapping("/{id}")
    public Categoria editCategoria(@PathVariable Integer id, @RequestBody @Valid CategoriaDTO catDTO) {
        Categoria categoria = service.fromDTO(catDTO);
        return service.editCategoria(id, categoria);
    }

    @DeleteMapping("/{id}")
    public Categoria removeCategoria(@PathVariable Integer id) {
        return service.deleteCategoria(id);
    }
}
