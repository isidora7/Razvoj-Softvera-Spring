package org.raflab.studsluzba.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Table(name = "nastavnik")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nastavnik {
	 
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;

	 @NotBlank
	 @Column(nullable = false, length = 60)
	 private String ime;

	@NotBlank @Column(nullable = false, length = 60)
	private String prezime;

	@Column(length = 60)
	private String srednjeIme;

	@Email
	@NotBlank
	@Column(nullable = false, unique = true, length = 120)
	private String email;

	@Column(length = 30)
	private String brojTelefona;

	@Column(length = 120)
	private String adresa;

	@OneToMany(mappedBy = "nastavnik",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<NastavnikZvanje> zvanja;

	@ManyToOne
	private SifarnikVisokoskolskihUstanova visokoskolskaUstanova;

	@Past
	private LocalDate datumRodjenja;

	@Column(length = 1) // "M" ili "F"
	private Character pol;

	@Pattern(regexp = "\\d{13}", message = "JMBG mora imati tačno 13 cifara")
	@Column(length = 13, unique = true)
	private String jmbg;

	//helperi za dodavanje zvanja, uklanjanje zvanja, i dohvatanje punog imena

	public void addZvanje(NastavnikZvanje z) {
		z.setNastavnik(this);
		zvanja.add(z);
	}
	public void removeZvanje(NastavnikZvanje z) {
		z.setNastavnik(null);
		zvanja.remove(z);
	}

	public String getPunoIme() {
		return (srednjeIme == null || srednjeIme.isBlank())
				? ime + " " + prezime
				: ime + " " + srednjeIme + " " + prezime;
	}





}