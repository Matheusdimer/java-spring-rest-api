package com.betha.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@JsonTypeName("boleto")
public class PagamentoComBoleto extends Pagamento {
    private LocalDate dataVencimento = LocalDate.now().plusDays(3L);
    private LocalDate dataPagamento;

    public PagamentoComBoleto() {

    }

    public PagamentoComBoleto(Pedido pedido) {
        super(pedido);
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
