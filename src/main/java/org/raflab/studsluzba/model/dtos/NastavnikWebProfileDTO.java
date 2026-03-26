package org.raflab.studsluzba.model.dtos;

import java.util.List;
import java.util.Map;

import org.raflab.studsluzba.model.Predmet;
import org.raflab.studsluzba.model.StudentIndeks;

/*
 * entitet koji se vraca kada se nastavnik uloguje na veb servis
 * sadrži:
 *  - predmete koje nastavnik predaje u aktivnoj skolskoj godini 
 *  - spiskove studenata koji slusaju predmet
 */

public class NastavnikWebProfileDTO {

	private List<Predmet> predmeti;
	private Map<Predmet,List<StudentIndeks>> slusajuPredmete;

}