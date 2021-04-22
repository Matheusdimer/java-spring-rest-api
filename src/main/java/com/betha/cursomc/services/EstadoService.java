package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cidade;
import com.betha.cursomc.domain.Estado;
import com.betha.cursomc.repositories.EstadoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {
    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> getAll() {
        return estadoRepository.findAll();
    }

    public Estado getOne(Integer id) {
        Optional<Estado> produto = estadoRepository.findById(id);

        return produto.orElseThrow(() -> new ObjectNotFoundException("Cidade com id " + id +
                " não encontrado."));
    }

    public Estado save(Estado estado) {
        estado.setId(null);

        return estadoRepository.save(estado);
    }

    public Estado edit(Integer id, Estado estado) {
        Optional<Estado> prod = estadoRepository.findById(id);

        if (!prod.isPresent()) {
            throw new ObjectNotFoundException("Cidade com id " + id + " não encontrado.");
        }
        estado.setId(id);
        return estadoRepository.save(estado);
    }

    public Estado delete(Integer id) {
        Optional<Estado> possivelEstado = estadoRepository.findById(id);

        if (!possivelEstado.isPresent()) {
            throw new ObjectNotFoundException("Produto com id " + id + " não encontrado.");
        }
        Estado estado = possivelEstado.get();
        estado.setCidades(Collections.emptyList());

        estadoRepository.delete(estado);
        return estado;
    }
}
