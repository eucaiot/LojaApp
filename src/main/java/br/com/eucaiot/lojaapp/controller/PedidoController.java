package br.com.eucaiot.lojaapp.controller;

import br.com.eucaiot.lojaapp.model.Pedido;
import br.com.eucaiot.lojaapp.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listarTodos(@PathVariable UUID id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> inserirPedido(@RequestBody Pedido pedido) {
        System.out.println(pedido.getItens());
        Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(pedidoSalvo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
