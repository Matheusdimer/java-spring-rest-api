package com.betha.cursomc.services;

import com.betha.cursomc.domain.*;
import com.betha.cursomc.dto.PedidoDTO;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.repositories.PedidoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Pedido add(PedidoDTO pedidoDTO) {
        Integer clienteId = pedidoDTO.getCliente();
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente id " + clienteId + " não encontrado"));

        Endereco endereco = null;
        for (Endereco end : cliente.getEnderecos()) {
            if (end.getId() == pedidoDTO.getEnderecoId()) {
                endereco = end;
            }
        };

        if (endereco == null) throw new ObjectNotFoundException("Endereco não encontrado");

        Pedido pedido = new Pedido(cliente, endereco);
        Pagamento pagamento = null;

        if (pedidoDTO.getPagamento() == 1) {
            pagamento = new PagamentoComBoleto(pedido, LocalDate.now(), LocalDate.now());
        } else if (pedidoDTO.getPagamento() == 2) {
            pagamento = new PagamentoComCartao(pedido, pedidoDTO.getParcelas());
        }

        pedido.setPagamento(pagamento);

        return pedidoRepository.save(pedido);
    }

    public Pedido delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente id " + id + " não encontrado"));

        pedidoRepository.delete(pedido);
        return pedido;
    }
}
