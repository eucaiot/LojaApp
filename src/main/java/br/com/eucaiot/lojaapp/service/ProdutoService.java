package br.com.eucaiot.lojaapp.service;

import br.com.eucaiot.lojaapp.dto.ProdutoDto;
import br.com.eucaiot.lojaapp.model.Categoria;
import br.com.eucaiot.lojaapp.model.Produto;
import br.com.eucaiot.lojaapp.repository.CategoriaRepository;
import br.com.eucaiot.lojaapp.repository.ProdutoRepository;
import br.com.eucaiot.lojaapp.exception.ObjectNotFoundException;
import br.com.eucaiot.lojaapp.utils.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository) {

        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto buscarPorId(UUID id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        return produto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado. Id: " +
                id + ". Tipo: " + Produto.class.getName()));
    }

    public Page<ProdutoDto> buscarPorFiltro(String nomeProduto, String categoria,
                                         Integer pagina, Integer elementosPorPagina,
                                         String ordem, String direcao) {

        String nomeProdutoDecodificado = URI.decodeString(nomeProduto);

        List<UUID> categoriasIds = URI.CategoriasLista(categoria);
        List<Categoria> categoriasEncontadas = categoriaRepository.findAllById(categoriasIds);

        PageRequest paginacao = PageRequest.of(pagina, elementosPorPagina, Sort.Direction.valueOf(direcao),
                ordem);

        Page<Produto> produtos = produtoRepository.buscaFiltrada(nomeProdutoDecodificado, categoriasEncontadas,
                paginacao);

        return produtos.map(ProdutoDto::new);
    }
}
