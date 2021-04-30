package com.betha.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime instante = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {

    }

    public Pedido(Cliente cliente, Endereco enderecoEntrega) {
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public Double getTotal() {
        Double soma = 0.00;
        for (ItemPedido item : getItens()) {
            soma += item.getSubtotal();
        }

        return soma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id.equals(pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        final StringBuffer sb = new StringBuffer("Pedido número: ");
        sb.append(id);
        sb.append(", Instante: ").append(this.getInstante().format(dtf));
        sb.append(", Cliente: ").append(this.getCliente().getNome());
        sb.append(", Situação do pagamento: ");
        sb.append(this.getPagamento().getEstadoPagamento().getDescricao());
        sb.append("\nDetalhes:\n");

        for (ItemPedido item : this.getItens()) {
            sb.append(item);
            sb.append("\n");
        }
        sb.append("\nValor total: ").append(nf.format(this.getTotal()));
        return sb.toString();
    }
}
