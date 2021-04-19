package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
    @GetMapping
    public List<Categoria> getCategorias() {
        Categoria categoria1 = new Categoria(1, "Informática");
        Categoria categoria2 = new Categoria(2, "Escritório");

        List<Categoria> lista = new ArrayList<>();
        lista.add(categoria1);
        lista.add(categoria2);

        return lista;
    }

}
