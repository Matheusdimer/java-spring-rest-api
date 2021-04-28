package com.betha.cursomc.dto;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ClienteUpdate
public class ClienteDTO {
    private Integer id;

    @NotEmpty
    @Length(min = 5, max = 120)
    private String nome;

    @NotEmpty
    @Email
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
