package com.betha.cursomc.domain;

import java.util.ArrayList;
import java.util.Collection;

public class CategoriaView {

    private Integer id;
    private String nome;

    private Collection<Produto> produtos = new ArrayList<>();

    public CategoriaView(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.produtos = categoria.getProdutos();
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Collection<Produto> getProdutos() {
        return produtos;
    }
}
