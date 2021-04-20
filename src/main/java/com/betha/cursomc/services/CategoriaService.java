package com.betha.cursomc.services;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.domain.CategoriaView;
import com.betha.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private EntityManager entityManager;

    public List<CategoriaView> getAll() {
        return entityManager
                .createNamedQuery("Categoria.findAllView", CategoriaView.class)
                .getResultList();
    }

    public Optional<Categoria> getCategoria(Integer id) {
        return repository.findById(id);
    }

    public Categoria saveCategoria(Categoria categoria) {
        categoria.setId(null);
        return repository.save(categoria);
    }

    public Categoria editCategoria(Integer id, Categoria categoria) {
        Optional<Categoria> cat = repository.findById(id);

        if (cat.isPresent()) {
            categoria.setId(id);
            return repository.save(categoria);
        } else {
            return null;
        }
    }

    public Categoria deleteCategoria(Integer id) {
        Optional<Categoria> categoria = repository.findById(id);

        if (categoria.isPresent()) {
            repository.delete(categoria.get());
            return categoria.get();
        } else {
            return null;
        }
    }

}
