package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.domain.dto.ProdutoDTO;
import com.betha.cursomc.resources.utils.URL;
import com.betha.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/search")
    public Page<ProdutoDTO> findPage(
            @RequestParam(name = "nome", defaultValue = "") String nome,
            @RequestParam(name = "categorias", defaultValue = "") String categorias,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "lines", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "orderby", defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction) {

        return service.search(URL.decodeParam(nome),
                URL.decodeIntList(categorias), page, linesPerPage, orderBy, direction)
                .map(ProdutoDTO::new);
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
