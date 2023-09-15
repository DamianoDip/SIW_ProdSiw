package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.FornitoreValidator;
import it.uniroma3.siw.model.Fornitore;
import it.uniroma3.siw.model.Prodotto;

import it.uniroma3.siw.service.FornitoreService;
import it.uniroma3.siw.service.ProdottoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class FornitoreController {



    @Autowired FornitoreService fornitoreService;
	@Autowired ProdottoService prodottoService;
	@Autowired FornitoreValidator fornitoreValidator;

	@GetMapping("/admin/indexAdminFornitore")
	public String getIndexFornitore(){
		return "admin/indexAdminFornitore.html";
	}

	@GetMapping("/fornitori")
	public String getFornitori( Model model){
		model.addAttribute("fornitori", this.fornitoreService.getAllFornitori());
		return "fornitori.html";

	}

	@GetMapping("/fornitore/{fornitore_id}")
	public String getFornitore( @PathVariable("fornitore_id") Long fornitore_id   ,Model model){
		Fornitore fornitore = this.fornitoreService.getFornitore(fornitore_id);
		model.addAttribute("fornitore", fornitore);
		return "fornitore.html";

	}

	@GetMapping("/admin/formNewFornitore")
	public String formNewFornitore (Model model) {
		model.addAttribute("fornitore", new Fornitore() );
		return "admin/formNewFornitore.html";
	}



	@GetMapping( "/admin/manageFornitori")
	public String manageFornitori(Model model) {
		List<Fornitore> fornitori = (List<Fornitore>) this.fornitoreService.getAllFornitori();
		model.addAttribute("fornitori" , fornitori ) ;
		return "admin/manageFornitori.html";
	}


	@Transactional
	@PostMapping("/admin/newFornitore")
	public String newFornitore( @Valid@ModelAttribute("fornitore") Fornitore fornitore , BindingResult bindingResult , Model model) {

		this.fornitoreValidator.validate(fornitore, bindingResult);

		if ( !bindingResult.hasErrors()) {
			model.addAttribute("fornitore", this.fornitoreService.saveFornitore(fornitore));
			return "fornitore.html";	
		}
		else {
			return "admin/formNewFornitore.html";
		}

	}


	@GetMapping("/admin/formUpdateFornitoreData/{old_fornitore_id}")
	public String formUpdateTickerData (@PathVariable("old_fornitore_id") Long old_id  ,Model model) {
		model.addAttribute("fornitore", new Fornitore() );
		model.addAttribute("oldFornitore",this.fornitoreService.getFornitore(old_id) );
		return "admin/formUpdateFornitoreData.html";
	}



	@Transactional
	@PostMapping("/admin/updateFornitoreData/{old_fornitore_id}")
	public String UpdatefornitoreData(   @PathVariable("old_fornitore_id") Long oldId  ,@Valid@ModelAttribute("fornitore") Fornitore fornitore , BindingResult bindingResult  , Model model) {

		this.fornitoreValidator.validate(fornitore, bindingResult);

		if ( !bindingResult.hasErrors()) {
			this.fornitoreService.updateFornitore(fornitore , oldId);
//			Fornitore fornitoreDaAggiornare = this.fornitoreRepository.findById(oldId).get();
//			fornitoreDaAggiornare.setNome(fornitore.getNome());
//			fornitoreDaAggiornare.setEmail(fornitore.getEmail());
//			fornitoreDaAggiornare.setIndirizzo(fornitore.getIndirizzo());
//			this.fornitoreRepository.save(fornitoreDaAggiornare);
			model.addAttribute("fornitori", this.fornitoreService.getAllFornitori());
			return "admin/manageFornitori.html";
		}
		else {
			return "admin/formUpdateFornitoreData.html";
		}
	}


	@Transactional
	@GetMapping("/admin/deleteFornitore/{id_fornitore}")
	public String deletefornitore ( @PathVariable("id_fornitore") Long id_fornitore , Model model) {
		
		this.fornitoreService.deleteFornitore(id_fornitore);

		model.addAttribute("fornitori", this.fornitoreService.getAllFornitori());

		return "admin/manageFornitori.html";

	}


	@GetMapping("/admin/formUpdateFornitori/{prod_id}")
	public String updateFornitore ( @PathVariable("prod_id") Long prod_id , Model model) {


		Prodotto prodotto = this.prodottoService.getProdotto(prod_id);


		model.addAttribute("fornitoreNotAssociatedToProduct", this.fornitoreService.getFornitoriNonAssociatiAProdotto(prodotto));

		model.addAttribute("fornitoreAssociatedToProduct", prodotto.getFornitori());

		model.addAttribute("prodotto", prodotto);


		return "admin/fornitoriToAdd.html";
	}






	@Transactional
	@GetMapping("/admin/addFornitore/{id_fornitore}/{id_prod}")
	public String addFornitore ( @PathVariable("id_fornitore") Long forn_id , @PathVariable("id_prod") Long id_prod , Model model) {

        Prodotto prod = this.fornitoreService.addFornitoreToProduct(forn_id , id_prod); 
//		Fornitore fornitoreDaAggiungere = this.fornitoreRepository.findById(forn_id).get();
//
//		Prodotto prod  = this.prodottoRepository.findById(id_prod).get();
//
//		prod.getFornitori().add(fornitoreDaAggiungere);
//
//
//		fornitoreDaAggiungere.getProdottiForniti().add(prod);
//
//		this.fornitoreRepository.save(fornitoreDaAggiungere);
//		this.prodottoRepository.save(prod);
//

		model.addAttribute("fornitoreNotAssociatedToProduct", this.fornitoreService.getFornitoriNonAssociatiAProdotto(prod));

		model.addAttribute("fornitoreAssociatedToProduct", prod.getFornitori());

		model.addAttribute("prodotto", prod);




		return "admin/fornitoriToAdd.html";
	}


	@Transactional
	@GetMapping("/admin/removeFornitore/{id_fornitore}/{id_prod}")
	public String removeFornitore ( @PathVariable("id_fornitore") Long forn_id , @PathVariable("id_prod") Long id_prod , Model model) {

		Prodotto prod  = this.fornitoreService.deleteFornitoreFromProduct(forn_id , id_prod);
//		Fornitore fornitoreDaRimuovere = this.fornitoreRepository.findById(forn_id).get();
//
//
//        prod.getFornitori().remove(fornitoreDaRimuovere) ;
//
//
//		fornitoreDaRimuovere.getProdottiForniti().remove(prod);
//
//		this.prodottoRepository.save(prod);
//		this.fornitoreRepository.save(fornitoreDaRimuovere);

		model.addAttribute("fornitoreNotAssociatedToProduct", this.fornitoreService.getFornitoriNonAssociatiAProdotto(prod));

		model.addAttribute("fornitoreAssociatedToProduct", prod.getFornitori());

		model.addAttribute("prodotto", prod);




		return "admin/fornitoriToAdd.html";
	}








}
