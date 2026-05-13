package br.com.springboot.lojaapp.repository;

import br.com.springboot.lojaapp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    @Transactional(readOnly = true)
    Cliente findByEmail(String email);
}
