package it.uniroma3.siw.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.ProdottoValidator;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.ImageRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ProdottoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class ProdottoController{


	@Autowired ProdottoService prodottoService;
	@Autowired ImageRepository imageRepository;
	@Autowired CredentialsService credentialsService;
	@Autowired ProdottoValidator prodottoValidator;




	@GetMapping("/admin/indexAdminProdotto")
	public String getIndexProdotto( Model model){

		return "admin/indexAdminProdotto.html";
	}


	@GetMapping("/prodotto/{prodotto_id}")
	public String getProdotto( @PathVariable("prodotto_id") Long prod_id   ,Model model){
		Prodotto prod = this.prodottoService.getProdotto(prod_id);
		model.addAttribute("prodotto", prod);
		model.addAttribute("fornitori", prod.getFornitori());


		Commento commento = this.prodottoService.commentoGiaScritto(prod);
		if ( commento!= null) {

			model.addAttribute("commento" , commento);
		}

		if ( prod.getCommenti() != null) {
			if ( commento!=null) prod.getCommenti().remove(commento);
			model.addAttribute( "commenti", prod.getCommenti());
		}	

		return "prodotto.html";

	}



	@GetMapping("/prodotti")
	public String getProdotti( Model model){
		model.addAttribute("prodotti", this.prodottoService.getAllProdotti());



		return "prodotti.html";

	}

	@GetMapping("/prodottiOrdinatiPerPrezzo")
	public String getProdottiOrdinatiPerPrezzo( Model model){


		model.addAttribute("prodotti", this.prodottoService.getProdottiOrdinatiPerPrezzo());
		return "prodotti.html";

	}


	@GetMapping("/prodottiOrdinatiPerNome")
	public String getProdottiOrdinatiPerNome( Model model){
		model.addAttribute("prodotti", this.prodottoService.getProdottiOrdinatiPerNome());
		return "prodotti.html";

	}



	@GetMapping("/admin/formNewProdotto")
	public String formNewProdotto (Model model) {
		model.addAttribute("prodotto", new Prodotto() );
		return "admin/formNewProdotto.html";
	}

	@Transactional
	@PostMapping("/admin/newProdotto")
	public String newProdotto( @RequestParam("file") MultipartFile image , @Valid@ModelAttribute("prodotto") Prodotto prodotto , BindingResult bindingResult , Model model) throws IOException{

		this.prodottoValidator.validate(prodotto, bindingResult);

		if ( !bindingResult.hasErrors()) {
			this.prodottoService.saveProdotto(image , prodotto);
			//			
			//			Image movieimg = new Image(image.getBytes());
			//			this.imageRepository.save(movieimg);
			//
			//			
			//			
			//			prodotto.setImage(movieimg);
			//
			//			this.prodottoRepository.save(prodotto);
			model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
			return "prodotti.html";

		}
		else {
			return "admin/formNewMovie.html";
		}

	}


	@GetMapping( "/admin/manageProdotti")
	public String manageProdotti(Model model) {
		model.addAttribute("prodotti" , this.prodottoService.getAllProdotti() ) ;
		return "admin/manageProdotti.html";
	}



	@GetMapping("/admin/formUpdateProdottoData/{old_prodotto_id}")
	public String formUpdateTickerData (@PathVariable("old_prodotto_id") Long old_id  ,Model model) {
		model.addAttribute("prodotto", new Prodotto() );
		model.addAttribute("oldProdotto",this.prodottoService.getProdotto(old_id));
		return "admin/formUpdateProdottoData.html";
	}



	@Transactional
	@PostMapping("/admin/updateProdottoData/{old_prodotto_id}")
	public String UpdateProdottoData( @RequestParam("file") MultipartFile image ,  @PathVariable("old_prodotto_id") Long oldId  ,@Valid@ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult  , Model model) throws IOException {

		this.prodottoValidator.validate(prodotto, bindingResult);

		if ( !bindingResult.hasErrors()) {
			this.prodottoService.updateProdotto(oldId , prodotto , image);
			//			Prodotto prodottoDaAggiornare = this.prodottoRepository.findById(oldId).get();
			//			prodottoDaAggiornare.setNome(prodotto.getNome());
			//			prodottoDaAggiornare.setPrezzo(prodotto.getPrezzo());
			//			prodottoDaAggiornare.setDescrizione(prodotto.getDescrizione());
			//
			//
			//			Image prodimg = new Image(image.getBytes());
			//			this.imageRepository.save(prodimg);
			//
			//			this.imageRepository.delete(prodottoDaAggiornare.getImage());
			//
			//			prodottoDaAggiornare.setImage(prodimg);
			//
			//			this.prodottoRepository.save(prodottoDaAggiornare);

			model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
			return "admin/manageProdotti.html";
		}

		else {
			return "admin/formUpdateProdottoData.html";
		}
	}


	@Transactional
	@GetMapping("/admin/deleteProdotto/{id_prodotto}")
	public String deleteProdotto ( @PathVariable("id_prodotto") Long id_prod , Model model) {

		this.prodottoService.deleteProdotto(id_prod);
		model.addAttribute("prodotti", this.prodottoService.getAllProdotti());
		return "/admin/manageProdotti.html";


	}













}
