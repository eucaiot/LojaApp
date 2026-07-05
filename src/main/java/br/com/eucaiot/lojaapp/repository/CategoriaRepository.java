package br.com.eucaiot.lojaapp.repository;

import br.com.eucaiot.lojaapp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID>  {

}
