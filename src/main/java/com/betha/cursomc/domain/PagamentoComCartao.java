package com.betha.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@Entity
@JsonTypeName("cartao")
public class PagamentoComCartao extends Pagamento {
    private Integer numeroParcelas;

    public PagamentoComCartao() {

    }

    public PagamentoComCartao(Pedido pedido, Integer numeroParcelas) {
        super(pedido);
        this.numeroParcelas = numeroParcelas;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
}
