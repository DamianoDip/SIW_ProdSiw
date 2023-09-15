package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.CommentoRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class CommentoService {
	
	
	@Autowired CommentoRepository commentoRepository;
	
	@Autowired CredentialsService credentialsService;
	
	@Autowired ProdottoService prodottoService;
	@Autowired ProdottoRepository prodottoRepository;

	
	public Commento getCommento(Long comm_id) {
		return this.commentoRepository.findById(comm_id).get();
	}
	
	@Transactional
	public void saveCommento(Long prod_id, @Valid Commento commento) {
		
		commento.setAutore(this.credentialsService.getCurrentUsername());
		
		Prodotto prodotto = this.prodottoService.getProdotto(prod_id);
		commento.setProdotto(prodotto);
		this.prodottoRepository.save(prodotto);
		this.commentoRepository.save(commento);
		
	}
	
	
    @Transactional
	public void deleteCommentoFromProduct(Long prod_id, Long comm_id) {
		
		Prodotto prodotto = this.prodottoRepository.findById(prod_id).get();

		Commento commento = this.commentoRepository.findById(comm_id).get();

		prodotto.getCommenti().remove(commento);

		this.prodottoRepository.save(prodotto);
		this.commentoRepository.delete(commento);
		
	}

    
    @Transactional
	public void updateComment(Long prodotto_id, Long commento_id , Commento commentoAggiornato) {
		Commento daAggiornare = this.commentoRepository.findById(commento_id).get();
		//Prodotto prodotto = this.prodottoRepository.findById(prodotto_id).get();

		daAggiornare.setTesto(commentoAggiornato.getTesto());

		this.commentoRepository.save(daAggiornare);
		
	}


    
    
	

}
