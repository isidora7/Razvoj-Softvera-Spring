package org.raflab.studsluzba.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RezultatIspitaResponse {

    private String indeks;
    private String student;
    private Integer poeniPredispit;
    private Integer poeniIspit;
    private Integer ukupnoPoena;
    private Integer ocena;
}
