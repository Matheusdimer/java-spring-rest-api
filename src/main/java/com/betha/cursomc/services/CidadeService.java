package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cidade;
import com.betha.cursomc.repositories.CidadeRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {
    @Autowired
    CidadeRepository cidadeRepository;

    public List<Cidade> getAll() {
        return cidadeRepository.findAll();
    }

    public Cidade getOne(Integer id) {
        Optional<Cidade> produto = cidadeRepository.findById(id);

        return produto.orElseThrow(() -> new ObjectNotFoundException("Cidade com id " + id +
                " não encontrado."));
    }

    public Cidade save(Cidade cidade) {
        cidade.setId(null);

        return cidadeRepository.save(cidade);
    }

    public Cidade edit(Integer id, Cidade cidade) {
        Optional<Cidade> prod = cidadeRepository.findById(id);

        if (!prod.isPresent()) {
            throw new ObjectNotFoundException("Cidade com id " + id + " não encontrado.");
        }
        cidade.setId(id);
        return cidadeRepository.save(cidade);
    }

    public Cidade delete(Integer id) {
        Optional<Cidade> possivelCidade = cidadeRepository.findById(id);

        if (!possivelCidade.isPresent()) {
            throw new ObjectNotFoundException("Produto com id " + id + " não encontrado.");
        }
        Cidade cidade = possivelCidade.get();
        cidade.setEstado(null);

        cidadeRepository.delete(cidade);
        return cidade;
    }
}
