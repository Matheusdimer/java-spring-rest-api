package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
    @Autowired
    private ProdutoService service;

    @GetMapping
    public List<Produto> getProdutos() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Integer id) {
        Produto produto = service.getProduto(id);

        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) {
        produto = service.saveProduto(produto);

        return ResponseEntity.created(URI.create("/produtos/" + produto.getId())).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> editProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        produto = service.editProduto(id, produto);

        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> removeProduto(@PathVariable Integer id) {
        Produto produto = service.deleteProduto(id);

        return ResponseEntity.ok(produto);
    }
}
