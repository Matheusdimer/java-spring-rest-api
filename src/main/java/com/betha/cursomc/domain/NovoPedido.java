package com.betha.cursomc.domain;

public class NovoPedido {
    private Integer cliente;
    private Integer enderecoId;
    private Integer pagamento;
    private Integer parcelas;

    public NovoPedido() {
    }

    public NovoPedido(Integer cliente, Integer enderecoId, Integer pagamento) {
        this.cliente = cliente;
        this.enderecoId = enderecoId;
        this.pagamento = pagamento;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }

    public Integer getPagamento() {
        return pagamento;
    }

    public void setPagamento(Integer pagamento) {
        this.pagamento = pagamento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }
}
