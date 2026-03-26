package org.raflab.studsluzba.model;

import lombok.Data;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

@Entity
@Data
public class NastavnikZvanje {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@PastOrPresent
	@Column(nullable = false)
	private LocalDate datumIzbora;// datum izbora ili reizbora

	@NotBlank
	@Column(nullable = false, length = 120)
	private String naucnaOblast; // sifarnik na klijentu - tabela u bazi bez veze, za sada String


	@NotBlank
	@Column(nullable = false, length = 120)
	private String uzaNaucnaOblast;  // sifarnik na klijentu - tabela u bazi bez veze, za sada String


	@NotBlank
	@Column(nullable = false, length = 80)
	private String zvanje;    // sifarnik na klijentu - tabela u bazi bez veze, za sada String

	@Column(nullable = false)
	private boolean aktivno; // da li je trenutno zvanje aktivno

	@ManyToOne
	private Nastavnik nastavnik;
}