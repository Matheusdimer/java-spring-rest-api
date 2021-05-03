package com.betha.cursomc.domain.dto;

import com.betha.cursomc.domain.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class CategoriaDTO {
    private Integer id;

    @NotEmpty
    @Length(min = 5, max = 80)
    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
