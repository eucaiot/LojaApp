package br.com.eucaiot.lojaapp.controller;

import br.com.eucaiot.lojaapp.dto.ProdutoDto;
import br.com.eucaiot.lojaapp.model.Produto;
import br.com.eucaiot.lojaapp.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable UUID id){
        Produto produto = produtoService.buscarPorId(id);

        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> buscaFiltrada(@RequestParam(defaultValue = "") String nomeProduto,
                                                          @RequestParam(defaultValue = "") String categoriasIds,
                                                          @RequestParam(defaultValue = "0") Integer pagina,
                                                          @RequestParam(defaultValue = "20") Integer elementosPorPagina,
                                                          @RequestParam(defaultValue = "ASC") String direcao,
                                                          @RequestParam(defaultValue = "nome") String ordem) {

        Page<ProdutoDto> produtoDtoPage = produtoService.buscarPorFiltro(nomeProduto, categoriasIds,
                pagina, elementosPorPagina, ordem, direcao);

        return ResponseEntity.ok().body(produtoDtoPage);

    }
}
