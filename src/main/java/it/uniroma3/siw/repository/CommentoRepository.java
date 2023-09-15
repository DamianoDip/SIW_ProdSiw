package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Commento;

public interface CommentoRepository extends CrudRepository<Commento, Long>{

	boolean existsByTestoAndAutore(String testo, String autore);

	boolean existsByTesto(String testo);

}
