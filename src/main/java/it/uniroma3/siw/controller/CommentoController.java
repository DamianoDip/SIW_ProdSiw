package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.CommentoValidator;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.ProdottoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class CommentoController {


	@Autowired
	CommentoService commentoService;



	@Autowired 
	ProdottoService prodottoService;

	@Autowired 
	CommentoValidator commentoValidator;


	@GetMapping("/default/creaCommento/{prod_id}")
	public String creaCommento ( @PathVariable("prod_id") Long prod_id , Model model ) {
		Prodotto prodotto = this.prodottoService.getProdotto(prod_id);

		if ( prodotto.getId()!= null) {
			model.addAttribute("prod_id", prodotto.getId());
		}
		else {
			return "error.html";
		}
		model.addAttribute("commento" , new Commento());

		return "default/formNewCommento.html";
	}


	@Transactional
	@PostMapping("/default/newCommento/{prod_id}")
	public String newCommento ( @PathVariable("prod_id") Long prod_id , @Valid @ModelAttribute("commento") Commento commento ,BindingResult bindingResult , Model model) {

		this.commentoService.saveCommento(prod_id, commento);
		model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
		return "prodotti.html";

	}

	@Transactional
	@GetMapping("/default/eliminaCommento/{prodotto_id}/{commento_id}")
	public String deleteComment (@PathVariable("prodotto_id") Long prod_id , @PathVariable("commento_id") Long comm_id , Model model) {


		this.commentoService.deleteCommentoFromProduct(prod_id,comm_id);





		model.addAttribute("prodotti", this.prodottoService.getAllProdotti());

		return ("prodotti.html");
	}



	@GetMapping("/default/modificaCommento/{prodotto_id}/{commento_id}")
	public String getFormUpdateComment(@PathVariable("prodotto_id") Long prod_id , @PathVariable("commento_id") Long comm_id ,Model model) {

		Commento commento = this.commentoService.getCommento(comm_id);
		Prodotto prodotto = this.prodottoService.getProdotto(prod_id);

		model.addAttribute("prodotto",prodotto);
		model.addAttribute("vecchioCommento", commento);

		model.addAttribute("nuovoCommento", new Commento());

		return "default/formUpdateCommento.html";
	}


	@PostMapping("default/aggiornaCommento/{prodotto_id}/{commento_id}")
	public String updateComment ( @PathVariable("prodotto_id") Long prodotto_id , @PathVariable("commento_id") Long commento_id , @Valid@ModelAttribute("nuovoCommento") Commento commentoAggiornato ,BindingResult bindingResult , Model model) {


		this.commentoService.updateComment(prodotto_id, commento_id, commentoAggiornato);
		model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
		return "prodotti.html";

	}




}
