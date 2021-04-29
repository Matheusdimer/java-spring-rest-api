package com.betha.cursomc.resources;

import com.betha.cursomc.dto.PedidoDTO;
import com.betha.cursomc.domain.Pedido;
import com.betha.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
    @Autowired
    PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getPedidos() {
        return pedidoService.getAll();
    }

    @GetMapping("/{id}")
    public Pedido getPedido(@PathVariable Integer id) {
        return pedidoService.getOne(id);
    }

    @PostMapping
    public Pedido addPedido(@RequestBody PedidoDTO pedido) {
        return pedidoService.add(pedido);
    }

    @DeleteMapping("/{id}")
    public Pedido removePedido(@PathVariable Integer id) {
        return pedidoService.delete(id);
    }
}
