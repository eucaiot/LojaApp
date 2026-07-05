package br.com.eucaiot.lojaapp.repository;

import br.com.eucaiot.lojaapp.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, UUID> {
}
