package com.betha.cursomc.domain;

import com.betha.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class PagamentoComBoleto extends Pagamento {
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    public PagamentoComBoleto() {

    }

    public PagamentoComBoleto(Pedido pedido, LocalDate dataVencimento, LocalDate dataPagamento) {
        super(pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
