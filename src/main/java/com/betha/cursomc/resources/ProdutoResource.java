package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Produto addProduto(@RequestBody Produto produto) {
        return service.saveProduto(produto);
    }
}
