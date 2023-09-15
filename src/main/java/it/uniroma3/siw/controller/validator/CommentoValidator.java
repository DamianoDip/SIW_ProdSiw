package it.uniroma3.siw.controller.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.repository.CommentoRepository;

@Component
public class CommentoValidator implements Validator {

	@Autowired CommentoRepository commentoRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Commento.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Commento commento = (Commento) target;
		
		if ( commento.getTesto()!= null 
				&&  commentoRepository.existsByTesto(commento.getTesto())){
			errors.reject("commento.duplicate");
		}
		
		
	}

}
