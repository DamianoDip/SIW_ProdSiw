package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Fornitore {
	
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome ;
	
	@NotBlank
	private String indirizzo;
	
	@NotBlank
	private String email;
	
    @ManyToMany
    private List<Prodotto> prodottiForniti;
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public List<Prodotto> getProdottiForniti() {
		return prodottiForniti;
	}

	public void setProdottiForniti(List<Prodotto> prodottiForniti) {
		this.prodottiForniti = prodottiForniti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, indirizzo, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornitore other = (Fornitore) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(indirizzo, other.indirizzo) && Objects.equals(nome, other.nome);
	}
	
	
	
}
