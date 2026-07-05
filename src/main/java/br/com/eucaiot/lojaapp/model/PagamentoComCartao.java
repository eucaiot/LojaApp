package br.com.eucaiot.lojaapp.model;

import br.com.eucaiot.lojaapp.model.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Entity;
import java.util.UUID;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Integer numeroDeParcelas;

    public PagamentoComCartao() {

    }

    public PagamentoComCartao(UUID id, EstadoPagamento estadoPagamento, Pedido pedido,
                              Integer numeroDeParcelas) {

        super(id, estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }


}
