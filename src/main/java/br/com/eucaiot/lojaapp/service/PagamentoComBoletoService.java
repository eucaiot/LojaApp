package br.com.eucaiot.lojaapp.service;

import br.com.eucaiot.lojaapp.model.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoComBoletoService {

    public void atrubuirDataVencimento(PagamentoComBoleto pagamento, LocalDateTime instante) {
        pagamento.setDataVencimento(instante.plusDays(2).toLocalDate());
    }
}
