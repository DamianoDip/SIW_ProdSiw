package it.uniroma3.siw.service;


import it.uniroma3.siw.model.Fornitore;
import it.uniroma3.siw.model.Prodotto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.repository.FornitoreRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;



@Service
public class FornitoreService {



	@Autowired FornitoreRepository fornitoreRepository;
	@Autowired ProdottoRepository prodottoRepository;

	public Iterable<Fornitore> getAllFornitori() {

		return this.fornitoreRepository.findAll();


	}

	public Fornitore getFornitore(Long fornitore_id) {
		return this.fornitoreRepository.findById(fornitore_id).get();
	}


	@Transactional
	public Fornitore saveFornitore(@Valid Fornitore fornitore) {

		return this.fornitoreRepository.save(fornitore);
	}

	@Transactional
	public Fornitore updateFornitore(@Valid Fornitore fornitore, Long oldId) {
		Fornitore fornitoreDaAggiornare = this.getFornitore(oldId);
		fornitoreDaAggiornare.setNome(fornitore.getNome());
		fornitoreDaAggiornare.setEmail(fornitore.getEmail());
		fornitoreDaAggiornare.setIndirizzo(fornitore.getIndirizzo());
		return this.saveFornitore(fornitoreDaAggiornare);


	}


	@Transactional
	public void deleteFornitore(Long id_fornitore) {

		Fornitore fornitoreDaEliminare = this.fornitoreRepository.findById(id_fornitore).get();
		this.fornitoreRepository.delete( fornitoreDaEliminare);

	}


	public List<Fornitore> getFornitoriNonAssociatiAProdotto ( Prodotto prodotto){

		List<Fornitore> fornitoriNotAssociatedToProduct = (List<Fornitore>) this.fornitoreRepository.findAll();
		fornitoriNotAssociatedToProduct.removeAll(prodotto.getFornitori());
		return fornitoriNotAssociatedToProduct;



	}


	@Transactional
	public Prodotto addFornitoreToProduct(Long forn_id, Long id_prod) {
		Fornitore fornitoreDaAggiungere = this.fornitoreRepository.findById(forn_id).get();
		Prodotto prod  = this.prodottoRepository.findById(id_prod).get();
		prod.getFornitori().add(fornitoreDaAggiungere);
		fornitoreDaAggiungere.getProdottiForniti().add(prod);
		this.fornitoreRepository.save(fornitoreDaAggiungere);
		return this.prodottoRepository.save(prod);
	}

	public Prodotto deleteFornitoreFromProduct(Long forn_id, Long id_prod) {

		Prodotto prod = this.prodottoRepository.findById(id_prod).get();
		Fornitore fornitoreDaRimuovere = this.fornitoreRepository.findById(forn_id).get();
		prod.getFornitori().remove(fornitoreDaRimuovere) ;
		fornitoreDaRimuovere.getProdottiForniti().remove(prod);
		this.fornitoreRepository.save(fornitoreDaRimuovere);
		return this.prodottoRepository.save(prod);

	}



}
