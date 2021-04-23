package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.Pedido;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.repositories.PedidoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<Pedido> getAll() {
        return pedidoRepository.findAll();
    }

    public Pedido getOne(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido id " + id + " não encontrado"));
    }

    public Pedido add(Pedido pedido) {
        Integer clienteId = pedido.getCliente().getId();
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido id " + clienteId + " não encontrado"));

        pedido.setId(null);
        pedido.setCliente(cliente);
        return pedidoRepository.save(pedido);
    }

    public Pedido delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido id " + id + " não encontrado"));

        pedidoRepository.delete(pedido);
        return pedido;
    }
}
