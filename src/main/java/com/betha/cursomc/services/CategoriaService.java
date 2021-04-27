package com.betha.cursomc.services;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.dto.CategoriaDTO;
import com.betha.cursomc.repositories.CategoriaRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoriaDTO> getAll() {
        /*
        return entityManager
                .createNamedQuery("Categoria.findAllView", CategoriaView.class)
                .getResultList();

        */
        List<Categoria> categorias = repository.findAll();

        return categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Categoria getCategoria(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
    }

    public Categoria saveCategoria(Categoria categoria) {
        categoria.setId(null);
        return repository.save(categoria);
    }

    public Categoria editCategoria(Integer id, Categoria categoria) {
        Categoria cat = getCategoria(id);

        categoria.setId(id);
        return repository.save(categoria);
    }

    public Categoria deleteCategoria(Integer id) {
        Categoria categoria = getCategoria(id);

        repository.delete(categoria);

        categoria.setProdutos(Collections.emptyList());
        return categoria;
    }

    public Page<Categoria> findPerPages(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO cat) {
        return new Categoria(cat.getId(), cat.getNome());
    }
}
