package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Fornitore;
import it.uniroma3.siw.repository.FornitoreRepository;


@Component
public class FornitoreValidator implements Validator {

	@Autowired FornitoreRepository fornitoreRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Fornitore.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Fornitore fornitore = (Fornitore) target;
		
		if ( fornitore.getNome()!= null
				&& fornitore.getIndirizzo()!= null
				   &&  fornitoreRepository.existsByNomeAndIndirizzo(fornitore.getNome(), fornitore.getIndirizzo())){
			errors.reject("fornitore.duplicate");
		}
		
		
	}

}
