package br.com.springboot.lojaapp.repository;

import br.com.springboot.lojaapp.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID>  {

}
