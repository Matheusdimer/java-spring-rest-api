package com.betha.cursomc.resources;

import com.betha.cursomc.domain.Categoria;
import com.betha.cursomc.domain.dto.CategoriaDTO;
import com.betha.cursomc.domain.dto.PedidoDTO;
import com.betha.cursomc.domain.Pedido;
import com.betha.cursomc.domain.enums.Perfil;
import com.betha.cursomc.security.UserSS;
import com.betha.cursomc.services.PedidoService;
import com.betha.cursomc.services.UserService;
import com.betha.cursomc.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
    @Autowired
    private PedidoService pedidoService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<Pedido> getPedidos() {
        return pedidoService.getAll();
    }

    @GetMapping("/{id}")
    public Pedido getPedido(@PathVariable Integer id) {
        UserSS user = UserService.authenticated();
        Pedido pedido = pedidoService.getOne(id);

        if (user == null || !pedido.getCliente().getId().equals(user.getId()) && !user.hasRole(Perfil.ADMIN)) {
            throw new AuthorizationException("Acesso negado");
        }

        return pedido;
    }

    @GetMapping("/cliente")
    public Page<Pedido> findPage(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "lines", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "orderby", defaultValue = "instante") String orderBy,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction) {
        return pedidoService.findPedidosCliente(page, linesPerPage, orderBy, direction);
    }

    @PostMapping
    public Pedido addPedido(@RequestBody PedidoDTO pedido) {
        return pedidoService.add(pedido);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePedido(@PathVariable Integer id) {
        pedidoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
