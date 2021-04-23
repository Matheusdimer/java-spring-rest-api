package com.betha.cursomc.services;

import com.betha.cursomc.domain.Cliente;
import com.betha.cursomc.domain.Pedido;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.repositories.PedidoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public Pedido add(Integer clienteId, Pedido pedido) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente id " + clienteId + " não encontrado"));

        pedido.setCliente(cliente);
        return pedidoRepository.save(pedido);
    }

    public Pedido delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente id " + id + " não encontrado"));

        pedidoRepository.delete(pedido);
        return pedido;
    }
}
