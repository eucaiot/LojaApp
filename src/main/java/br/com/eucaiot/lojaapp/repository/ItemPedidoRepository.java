package br.com.eucaiot.lojaapp.repository;

import br.com.eucaiot.lojaapp.model.ItemPedido;
import br.com.eucaiot.lojaapp.model.ItemPedidoPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPk> {
}
