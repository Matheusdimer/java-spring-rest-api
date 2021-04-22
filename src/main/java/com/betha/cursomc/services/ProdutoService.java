package com.betha.cursomc.services;

import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.repositories.CategoriaRepository;
import com.betha.cursomc.repositories.ProdutoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
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

        return produto.orElseThrow(() -> new ObjectNotFoundException("Produto com id " + id +
                " não encontrado."));
    }

    public Produto saveProduto(Produto produto) {
        produto.setId(null);

        return produtoRepository.save(produto);
    }

    public Produto editProduto(Integer id, Produto produto) {
        Optional<Produto> prod = produtoRepository.findById(id);

        if (!prod.isPresent()) {
            throw new ObjectNotFoundException("Produto com id " + id + " não encontrado.");
        }
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public Produto deleteProduto(Integer id) {
        Optional<Produto> possivelProduto = produtoRepository.findById(id);

        if (!possivelProduto.isPresent()) {
            throw new ObjectNotFoundException("Produto com id " + id + " não encontrado.");
        }
        Produto produto = possivelProduto.get();
        produto.setCategorias(Collections.emptyList());

        produtoRepository.delete(produto);
        return produto;
    }
}
