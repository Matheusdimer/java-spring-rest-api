package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cidade;
import com.betha.cursomc.domain.Estado;
import com.betha.cursomc.repositories.CidadeRepository;
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
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Estado> getAll() {
        return estadoRepository.findAll();
    }

    public Estado getOne(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Estado com id " + id + " não encontrado."));
    }

    public Estado save(Estado estado) {
        estado.setId(null);

        return estadoRepository.save(estado);
    }

    public Estado edit(Integer id, Estado estado) {
        this.getOne(id);
        estado.setId(id);
        return estadoRepository.save(estado);
    }

    public Estado delete(Integer id) {
        Estado estado = this.getOne(id);

        estado.setCidades(Collections.emptyList());

        estadoRepository.delete(estado);
        return estado;
    }

    public List<Cidade> getCidades(Integer estadoId) {
        return cidadeRepository.findAllByEstadoId(estadoId);
    }

    public Cidade addCidade(Integer estadoId, Cidade cidade) {
        Estado estado = this.getOne(estadoId);

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
}
