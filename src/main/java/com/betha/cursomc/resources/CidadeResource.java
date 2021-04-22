package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Cidade;
import com.betha.cursomc.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {
    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public List<Cidade> getCidades() {
        return cidadeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> getCidade(@PathVariable Integer id) {
        Cidade cidade = cidadeService.getOne(id);

        return ResponseEntity.ok(cidade);
    }

    @PostMapping
    public ResponseEntity<Cidade> addProduto(@RequestBody Cidade cidade) {
        cidade = cidadeService.save(cidade);

        return ResponseEntity.created(URI.create("/produtos/" + cidade.getId())).body(cidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> editCidade(@PathVariable Integer id, @RequestBody Cidade cidade) {
        cidade = cidadeService.edit(id, cidade);

        return ResponseEntity.ok(cidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> removeCidade(@PathVariable Integer id) {
        Cidade cidade = cidadeService.delete(id);

        return ResponseEntity.ok(cidade);
    }
}
