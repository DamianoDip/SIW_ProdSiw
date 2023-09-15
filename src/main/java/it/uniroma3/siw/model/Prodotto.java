package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Prodotto {

	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Min(0)
	private float prezzo;
	
	
    @ManyToMany(mappedBy = "prodottiForniti")
    private List<Fornitore> fornitori;
	
	@NotBlank
	private String descrizione;

	@OneToOne	
	private Image image ;

	
    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL)
    private List<Commento> commenti;
	

	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}


	public List<Fornitore> getFornitori() {
		return fornitori;
	}


	public void setFornitori(List<Fornitore> fornitori) {
		this.fornitori = fornitori;
	}


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


	public float getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, nome, prezzo);
	}


	public List<Commento> getCommenti() {
		return commenti;
	}


	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prodotto other = (Prodotto) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Float.floatToIntBits(prezzo) == Float.floatToIntBits(other.prezzo);
	}


	

	
	
	
}
