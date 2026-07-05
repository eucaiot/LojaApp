package br.com.springboot.lojaapp.service;

import br.com.springboot.lojaapp.dto.ClienteDto;
import br.com.springboot.lojaapp.dto.ClienteNewDto;
import br.com.springboot.lojaapp.dto.EnderecoNewDto;
import br.com.springboot.lojaapp.model.Cidade;
import br.com.springboot.lojaapp.model.Cliente;
import br.com.springboot.lojaapp.model.Cpf;
import br.com.springboot.lojaapp.model.Endereco;
import br.com.springboot.lojaapp.repository.ClienteRepository;
import br.com.springboot.lojaapp.repository.EnderecoRepository;
import br.com.springboot.lojaapp.exception.DataIntegrityException;
import br.com.springboot.lojaapp.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    public Cliente buscarClientePorId(UUID id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado. Id:" +
                id + ". Tipo: " + Cliente.class.getName()));

    }

    public List<ClienteDto> buscarTodosCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(ClienteDto::new).collect(Collectors.toList());
    }

    public void atualizarCliente(ClienteDto clienteDto, UUID id) {
        Cliente cliente = buscarClientePorId(id);
        atualizaDadosCliente(cliente, clienteDto);
        clienteRepository.save(cliente);

    }

    private void atualizaDadosCliente(Cliente cliente, ClienteDto clienteDto) {
        cliente.setNome(clienteDto.nome());
        cliente.setEmail(clienteDto.email());
    }

    public Page<ClienteDto> buscarTodosComPaginacao(Integer pagina,
                                                 Integer elementosPorPagina,
                                                 String direcao, String ordenarPor) {

        PageRequest page = PageRequest.of(pagina, elementosPorPagina, Sort.Direction.valueOf(direcao),
                ordenarPor);

        Page<Cliente> clientes = clienteRepository.findAll(page);

        return clientes.map(ClienteDto::new);
    }

    public void deletarCliente(UUID id) {
        buscarClientePorId(id);
        try{
            clienteRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir cliente que contém pedidos.");
        }
    }


    public Cliente salvarCliente(ClienteNewDto clienteNewDto) {
        Cliente cliente = toCliente(clienteNewDto);
        clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());

        return cliente;
    }

    private Cliente toCliente(ClienteNewDto clienteNewDto){
        Cliente cliente = new Cliente();

        cliente.setNome(clienteNewDto.nome());
        cliente.setEmail(clienteNewDto.email());
        cliente.setCpf(new Cpf(clienteNewDto.cpf()));

        if (clienteNewDto.telefones() != null) {
            cliente.getTelefones().addAll(clienteNewDto.telefones());
        }

        EnderecoNewDto enderecoDto = clienteNewDto.endereco();
        if (enderecoDto != null) {
            Cidade cidade = new Cidade();
            cidade.setId(enderecoDto.cidadeId());

            Endereco endereco = new Endereco();
            endereco.setLogradouro(enderecoDto.logradouro());
            endereco.setNumero(enderecoDto.numero());
            endereco.setComplemento(enderecoDto.complemento());
            endereco.setBairro(enderecoDto.bairro());
            endereco.setCep(enderecoDto.cep());
            endereco.setCidade(cidade);
            endereco.setCliente(cliente);

            cliente.getEnderecos().add(endereco);
        }

        return cliente;
    }
}
