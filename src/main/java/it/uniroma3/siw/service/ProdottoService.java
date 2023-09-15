package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.ImageRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProdottoService {
	
	@Autowired ProdottoRepository prodottoRepository;
	@Autowired CredentialsService credentialsService;
	@Autowired ImageRepository imageRepository;

	public Prodotto getProdotto(Long prod_id) {
		return this.prodottoRepository.findById(prod_id).get();
	}
	
	
	public Commento commentoGiaScritto( Prodotto prod) {

		for ( Commento c : prod.getCommenti()) {
			if ( c!= null) {
				if ( c.getAutore().equals(this.credentialsService.getCurrentUsername())) {
					return c;

				}
			}
		}

		return null;
	}


	public List<Prodotto> getAllProdotti() {
		return (List<Prodotto>)  this.prodottoRepository.findAll();
		
	}


	public List<Prodotto> getProdottiOrdinatiPerPrezzo() {
		List<Prodotto> prodotti = this.getAllProdotti();

		Collections.sort(prodotti, new Comparator<Prodotto>() {


			public int compare(Prodotto p1, Prodotto p2) {

				if ( Float.compare(p1.getPrezzo(), p2.getPrezzo())== 0) {
					return p1.getNome().compareTo(p2.getNome());
				}

				return Float.compare(p1.getPrezzo(), p2.getPrezzo());

			}
		});
		
		return prodotti;
	}
	
	
	
	public List<Prodotto> getProdottiOrdinatiPerNome() {
		List<Prodotto> prodotti = this.getAllProdotti();

		Collections.sort(prodotti, new Comparator<Prodotto>() {


			public int compare(Prodotto p1, Prodotto p2) {

				if ( p1.getNome().compareTo(p2.getNome())== 0) {
					return Float.compare(p1.getPrezzo(), p2.getPrezzo());
				}

				return p1.getNome().compareTo(p2.getNome());

			}
		});
		
		return prodotti;
	}


	@Transactional
	public void deleteProdotto(Long id_prod) {
		
		Prodotto prodottoDaEliminare = this.prodottoRepository.findById(id_prod).get();
		this.prodottoRepository.delete( prodottoDaEliminare);
		
	}


	
	
	@Transactional
	public void saveProdotto(MultipartFile image, @Valid Prodotto prodotto) throws IOException {
		// TODO Auto-generated method stub
		

		Image movieimg = new Image(image.getBytes());
		this.imageRepository.save(movieimg);

		
		
		prodotto.setImage(movieimg);

		this.prodottoRepository.save(prodotto);

		
	}


	public void updateProdotto(Long oldId, @Valid Prodotto prodotto, MultipartFile image) throws IOException {
		
		Prodotto prodottoDaAggiornare = this.prodottoRepository.findById(oldId).get();
		prodottoDaAggiornare.setNome(prodotto.getNome());
		prodottoDaAggiornare.setPrezzo(prodotto.getPrezzo());
		prodottoDaAggiornare.setDescrizione(prodotto.getDescrizione());


		Image prodimg = new Image(image.getBytes());
		this.imageRepository.save(prodimg);

		this.imageRepository.delete(prodottoDaAggiornare.getImage());

		prodottoDaAggiornare.setImage(prodimg);

		this.prodottoRepository.save(prodottoDaAggiornare);
		
	}

}
