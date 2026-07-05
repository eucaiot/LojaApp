package br.com.eucaiot.lojaapp.service;

import br.com.eucaiot.lojaapp.dto.CategoriaDto;
import br.com.eucaiot.lojaapp.model.Categoria;
import br.com.eucaiot.lojaapp.repository.CategoriaRepository;
import br.com.eucaiot.lojaapp.exception.DataIntegrityException;
import br.com.eucaiot.lojaapp.exception.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.*;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria buscarPorId(UUID id) {
        Optional<Categoria> objeto = categoriaRepository.findById(id);
        return objeto.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada. Id: " +
                id + ". Tipo: " + Categoria.class.getName()));
    }

    public List<CategoriaDto> buscarTodos(){
        List<Categoria> categorias = categoriaRepository.findAll();

        return categorias.stream().map(CategoriaDto::new).collect(Collectors.toList());
    }

   public Page<CategoriaDto> buscarTodosComPaginacao(Integer pagina, Integer elementosPorPagina, String direcao,
                                      String odenarPor) {
       PageRequest paginacao = PageRequest.of(pagina, elementosPorPagina, Direction.valueOf(direcao), odenarPor);

       Page<Categoria> categorias = categoriaRepository.findAll(paginacao);

       return categorias.map(CategoriaDto::new);
   }

    public Categoria salvarCategoria(CategoriaDto categoria){
        Categoria entidade = new Categoria(categoria.nome(), null);
        return categoriaRepository.save(entidade);
    }

    public void atualizarCategoria(CategoriaDto categoriaDto, UUID id){
        Categoria categoria = buscarPorId(id);
        atualizaDadosCategoria(categoria, categoriaDto);
        categoriaRepository.save(categoria);
    }

    private void atualizaDadosCategoria(Categoria categoria, CategoriaDto categoriaDto) {
        categoria.setNome(categoriaDto.nome());
    }


    public void deletarCategoria(UUID id) {
        buscarPorId(id);
        try{
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Não é possível excluir categoria que contém produtos");
        }
    }

    private Categoria toCategoria (CategoriaDto categoriaDto){
        return new Categoria(categoriaDto.nome(), categoriaDto.id());
    }
}
