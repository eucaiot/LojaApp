package br.com.eucaiot.lojaapp.dto;

import br.com.eucaiot.lojaapp.model.Produto;

import java.io.Serializable;
import java.util.UUID;

public record ProdutoDto(
        UUID id,
        String nome,
        Double preco
) implements Serializable {

    public ProdutoDto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco());
    }
}
