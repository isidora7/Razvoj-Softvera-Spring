package org.raflab.studsluzba.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IzlazakNaIspitResponse {

    private Long id;
    private String indeks;
    private String student;
    private String predmet;
    private Integer poeniPredispit;
    private Integer poeniIspit;
    private Integer ukupnoPoena;
    private Integer ocena;
    private Integer brojPolaganja;

}
