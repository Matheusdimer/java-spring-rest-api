package com.betha.cursomc.dto;

import com.betha.cursomc.domain.Pagamento;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PedidoDTO {
    @NotNull
    private Integer cliente;
    @NotNull
    private Integer endereco;
    @NotNull
    private Pagamento pagamento;

    @NotNull
    private List<ItemPedidoDTO> itens;

    public PedidoDTO() {
    }

    public PedidoDTO(Integer cliente, Integer endereco, Pagamento pagamento, List<ItemPedidoDTO> itens) {
        this.cliente = cliente;
        this.endereco = endereco;
        this.pagamento = pagamento;
        this.itens = itens;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getEndereco() {
        return endereco;
    }

    public void setEndereco(Integer endereco) {
        this.endereco = endereco;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
