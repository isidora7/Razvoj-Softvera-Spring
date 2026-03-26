package org.raflab.studsluzba.model;

import lombok.Data;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Data
public class StudentPodaci {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;
	 private String ime;
	 private String prezime;
	 private String srednjeIme;
	 private String jmbg;    
	 private LocalDate datumRodjenja;
	 private String mestoRodjenja;
	 private String drzavaRodjenja;   
	 private String drzavljanstvo;
	 private String nacionalnost;
	 private Character pol;
	 private String mestoPrebivalista;
	 private String adresaPrebivalista;
	 private String mestoStanovanja;
	 private String adresaStanovanja;
	 private String brojTelefonaMobilni;  
	 private String brojTelefonaFiksni;
	 private String emailFakultetski;
	 private String emailPrivatni;
	 private String brojLicneKarte;
	 private String licnuKartuIzdao;

	 @ManyToOne
	 private SifarnikSrednjihSkola zavrsenaSrednjaSkola;

	 private double uspehIzSrednjeSkole;
	 private double uspehSaPrijemnog;

	 @ManyToOne
	 private SifarnikVisokoskolskihUstanova prelazSaDrugeVisokoskolskeUstanove;



}
