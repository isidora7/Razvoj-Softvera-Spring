package org.raflab.studsluzba.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Getter
@Setter
@ToString(exclude = {"studProgram"})
public class Predmet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String sifra;

	@NotBlank
	@Column(nullable = false)
	private String naziv;

	private String opis;

	@NotNull
	@Column(nullable = false)
	private Integer espb;

	@ManyToOne
	private StudijskiProgram studProgram;

	private boolean obavezan;
	private int semestar;
	private int brojCasovaPredavanja;
	private int brojCasovaVezbi;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sifra == null) ? 0 : sifra.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Predmet other = (Predmet) obj;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		return true;
	}

	public void setStudijskiProgram(StudijskiProgram sp) {
		this.studProgram = sp;
	}

	public StudijskiProgram getStudijskiProgram() {
		return this.studProgram;
	}
}