package com.betha.cursomc.services;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.repositories.CategoriaRepository;
import com.betha.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Produto> getAll() {
        return produtoRepository.findAll();
    }

    public Produto getProduto(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            return produto.get();
        } else {
            return null;
        }
    }

    public Produto saveProduto(Produto produto) {
        produto.setId(null);

        return produtoRepository.save(produto);
    }

    public Produto editProduto(Integer id, Produto produto) {
        Optional<Produto> prod = produtoRepository.findById(id);

        if (prod.isPresent()) {
            produto.setId(id);
            return produtoRepository.save(produto);
        } else {
            return null;
        }
    }

    public Produto deleteProduto(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isPresent()) {
            produtoRepository.delete(produto.get());
            return produto.get();
        } else {
            return null;
        }
    }
}
