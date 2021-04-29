package com.betha.cursomc.services;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.repositories.CategoriaRepository;
import com.betha.cursomc.repositories.ProdutoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProdutoService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Produto> getAll() {
        return produtoRepository.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Produto getProduto(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto com id " + id + " não encontrado."));
    }

    public Produto saveProduto(Produto produto) {
        produto.setId(null);

        Produto produtoSalvo = produtoRepository.save(produto);
        entityManager.flush();
        entityManager.refresh(produtoSalvo);

        return produtoSalvo;
    }

    public Produto editProduto(Integer id, Produto produto) {
        Produto prod = this.getProduto(id);
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    public Produto deleteProduto(Integer id) {
        Produto produto = this.getProduto(id);

        produto.setCategorias(Collections.emptyList());

        produtoRepository.delete(produto);
        return produto;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Produto> search(String nome, List<Integer> catIds,
                                Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAllById(catIds);

        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
