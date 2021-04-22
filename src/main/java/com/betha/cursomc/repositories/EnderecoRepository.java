package com.betha.cursomc.repositories;

import com.betha.cursomc.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    public List<Endereco> findByClienteId(Integer clienteId);
}
