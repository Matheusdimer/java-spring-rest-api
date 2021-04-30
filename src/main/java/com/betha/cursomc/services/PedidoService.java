package com.betha.cursomc.services;

import com.betha.cursomc.domain.*;
import com.betha.cursomc.dto.ItemPedidoDTO;
import com.betha.cursomc.dto.PedidoDTO;
import com.betha.cursomc.repositories.ClienteRepository;
import com.betha.cursomc.repositories.ItemPedidoRepository;
import com.betha.cursomc.repositories.PedidoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private EmailService emailService;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Pedido> getAll() {
        return pedidoRepository.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
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
            if (end.getId().equals(pedidoDTO.getEndereco())) {
                endereco = end;
            }
        }

        if (endereco == null) throw new ObjectNotFoundException("Endereco não encontrado");

        Pedido pedido = new Pedido(cliente, endereco);
        pedido.setPagamento(pedidoDTO.getPagamento());
        pedido.getPagamento().setPedido(pedido);

        for (ItemPedidoDTO item : pedidoDTO.getItens()) {
            Produto produto = produtoService.getProduto(item.getProdutoId());
            ItemPedido itemPedido = new ItemPedido(pedido, produto,
                    item.getDesconto(), item.getQuantidade(), produto.getPreco());
            pedido.getItens().add(itemPedido);
        }

        Pedido novoPedido = pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());

        emailService.sendOrderConfirmationEmail(novoPedido);

        return novoPedido;
    }

    public void delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente id " + id + " não encontrado"));

        pedidoRepository.delete(pedido);
    }
}
