package com.betha.cursomc;

import com.betha.cursomc.domain.ItemPedido;
import com.betha.cursomc.domain.Pedido;
import com.betha.cursomc.domain.Produto;
import com.betha.cursomc.repositories.ItemPedidoRepository;
import com.betha.cursomc.repositories.PedidoRepository;
import com.betha.cursomc.repositories.ProdutoRepository;
import com.betha.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		/*
		Pedido pedido = pedidoRepository.findById(9).
				orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
		Pedido pedido2 = pedidoRepository.findById(10).
				orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
		Produto prod1 = produtoRepository.findById(1).
				orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
		Produto prod2 = produtoRepository.findById(2).
				orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));


		ItemPedido item1 = new ItemPedido(pedido, prod1, 0.00, 1, 2000.00);
		ItemPedido item2 = new ItemPedido(pedido, prod2, 0.00, 2, 80.00);
		ItemPedido item3 = new ItemPedido(pedido2, prod2, 100.00, 1, 800.00);

		pedido.getItens().addAll(Arrays.asList(item1, item2));
		pedido2.getItens().addAll(Arrays.asList(item3));

		prod1.getItens().add(item1);
		prod2.getItens().add(item2);
		prod2.getItens().add(item3);

		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2));
		pedidoRepository.saveAll(Arrays.asList(pedido, pedido2));
		*/
	}
}
